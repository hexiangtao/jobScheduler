package com.ykn.jobscheduler.other;

import java.io.IOException;
import java.util.List;

/**
 * @author hexiangtao
 * @date 2022/4/3 10:20
 **/
public interface CsvFileLoader {


    List<String> load(String path) throws IOException;

    static CsvFileLoader create() {
        return new ChunkCsvFileLoader();
    }

}
