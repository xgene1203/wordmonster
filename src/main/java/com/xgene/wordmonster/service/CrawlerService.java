package com.xgene.wordmonster.service;

import com.xgene.wordmonster.model.Transcript;
import com.xgene.wordmonster.model.U2BResource;
import com.xgene.wordmonster.utils.Xml;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class CrawlerService {
    private static final String INFO_PATH = "https://youtube.com/get_video_info?video_id=";
    private final static Pattern getSubTitlePattern = Pattern.compile("https:\\/\\/www\\.youtube\\.com\\/api\\/timedtext\\?.*?(https:\\/\\/www\\.youtube\\.com\\/api\\/timedtext\\?.*key=yt8\\\\u0026lang=en)");

    private Optional<String> callApi(URI path, HttpMethod httpMethod) {
        ClientHttpRequest request = new OkHttp3ClientHttpRequestFactory().createRequest(path, httpMethod);
        try (ClientHttpResponse response = request.execute()) {
            String responseResult = new String(FileCopyUtils.copyToByteArray(response.getBody()), StandardCharsets.UTF_8);
            return StringUtils.isEmpty(responseResult) ? Optional.empty() : Optional.of(responseResult);
        } catch (IOException e) {
            log.warn("Encounter error api path:{}, errMsg:{}", path.getPath(), ExceptionUtils.getStackTrace(e));
        }
        return Optional.empty();
    }

    private <T> Optional<T> callXmlApiReturnPojo(URI path, HttpMethod httpMethod, Class<T> tClass) {
        ClientHttpRequest request = new OkHttp3ClientHttpRequestFactory().createRequest(path, httpMethod);
        try (ClientHttpResponse response = request.execute()) {
            JAXBContext context2 = null;
            context2 = JAXBContext.newInstance(Transcript.class);
            Unmarshaller unmarshaller = context2.createUnmarshaller();
            T timeLine = tClass.cast(unmarshaller.unmarshal(response.getBody()));
            return Optional.of(timeLine);
        } catch (IOException e) {
            log.warn("Encounter error api path:{}, errMsg:{}", path.getPath(), ExceptionUtils.getStackTrace(e));
        } catch (JAXBException e) {
            log.warn("Encounter error api path:{}, JAXBException:{}", path.getPath(), ExceptionUtils.getStackTrace(e));
        }
        return Optional.empty();
    }

    private Optional<String> getInfo(URI path) {
        String decodePath = null;
        if (callApi(path, HttpMethod.GET).isPresent()) {
            String utf8 = URLDecoder.decode(callApi(path, HttpMethod.GET).get(), Charset.defaultCharset());
            log.info("utf8:\n{}\n", utf8);
            Matcher matcher = getSubTitlePattern.matcher(utf8);
            String enSubtitlesPath = null;
            while (matcher.find()) {
                enSubtitlesPath = matcher.group(1);
            }
            decodePath = enSubtitlesPath.replaceAll("\\\\u0026", "&");
            log.info("decodePath:\n{}\n, sub\n:{}\n", decodePath, enSubtitlesPath);
        }
        return StringUtils.isEmpty(decodePath) ? Optional.empty() : Optional.of(decodePath);
    }

    private Transcript getSubtitles(String url) {
        URI path = URI.create(url);
        Optional<String> response = callApi(path, HttpMethod.GET);
        if (response.isPresent()) {
            Transcript transcript = Xml.toTranscript(response.get());
            transcript.getText().forEach(text -> text.setEnd(text.getStart() + text.getDur()));
            return transcript;
        }
        return null;
    }

    public U2BResource crawlU2BSubtitles(String videoId) {
        URI path = URI.create(INFO_PATH + videoId);
        Optional<String> info = getInfo(path);
        if (info.isPresent()) {
            U2BResource u2BResource = new U2BResource();
            u2BResource.setVideoPath(path.getPath());
            u2BResource.setEnSubtitlePath(info.get());
            u2BResource.setChSubtitlePath(info.get() + "&tlang=zh-Hant");
            u2BResource.setChSubTitle(getSubtitles(u2BResource.getChSubtitlePath()));
            u2BResource.setEnSubTitle(getSubtitles(u2BResource.getEnSubtitlePath()));
            log.info("u2BResource:{}", u2BResource);
            return u2BResource;
        }
        return null;
    }

    public static void main(String[] args) {
        CrawlerService crawlerService = new CrawlerService();
        crawlerService.crawlU2BSubtitles("jcdIqTFgWRA");
    }
}
