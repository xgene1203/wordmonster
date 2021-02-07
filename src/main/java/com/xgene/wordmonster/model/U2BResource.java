package com.xgene.wordmonster.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class U2BResource {
    private String videoId;
    private String videoPath;
    private String enSubtitlePath;
    private String chSubtitlePath;
    private Transcript enSubTitle;
    private Transcript chSubTitle;
}
