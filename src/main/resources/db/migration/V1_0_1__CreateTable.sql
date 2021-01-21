-- taiwan_test_central: http://www.taiwantestcentral.com/
DROP TYPE IF EXISTS word_category;
-- create type word_category as enum('CAP_I', 'CAP_II', 'SAT_I', 'SAT_II');

CREATE TABLE if not exists word (
    id INT NOT NULL PRIMARY KEY,
    category VARCHAR not null,
    english VARCHAR NOT NULL,
    chinese VARCHAR NOT NULL,
    freq int not null default 0,
    freq_link VARCHAR NULL
);