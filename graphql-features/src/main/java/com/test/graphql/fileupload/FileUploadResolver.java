package com.test.graphql.fileupload;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.test.graphql.generated.DgsConstants;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@DgsComponent
@Slf4j
public class FileUploadResolver {

    @DgsMutation(field = DgsConstants.MUTATION.UploadScriptWithMultipartPOST)
    public boolean uploadScript(DataFetchingEnvironment dfe) throws IOException {
        MultipartFile file = dfe.getArgument("input");
        String content = new String(file.getBytes());
        return ! content.isEmpty();
    }
}
