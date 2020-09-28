package com.example.module.controller;

import com.example.module.model.MultilevelModel;
import com.example.module.response.Response;
import com.example.module.response.Status;
import com.example.module.service.MinioService;
import com.example.module.service.PdfService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;

/**
 * Created by DIPU on 7/27/20
 */
@RestController
@RequestMapping("/test")
@Log4j2
public class Controller {

    @Autowired
    private MinioService minioService;

    @Autowired
    private PdfService pdfService;

    @GetMapping
    public ResponseEntity<Response> testAPI()
    {
        log.info("testing first api creation :D !");
        String ss="JULY".toLowerCase();
        log.info(WordUtils.capitalize(ss));

        return ResponseEntity.ok(new Response());
    }

    /**
     * to upload byte[] to controller
     * @param file
     * @param fileName
     * @return
     */
    @PostMapping("/file/upload1")
    public ResponseEntity<Response> uploadFile(@RequestBody byte[] file,
                                                      @RequestParam("fileName") String fileName)
    {

        boolean bool=minioService.uploadByUpdatedAPI(fileName, file);
        Response response;
        if(bool)
            response=new Response(HttpStatus.CREATED,Status.SUCCESS, bool, Collections.emptyList());
        else
            response=new Response(HttpStatus.INTERNAL_SERVER_ERROR, Status.ERROR, bool, Collections.emptyList());
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/file/upload2", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Response> uploadFile2(HttpServletRequest request, @RequestPart(value = "file", required = false) MultipartFile files)
    {
        Response response;
        try
        {
            byte[] bytes= IOUtils.toByteArray(files.getInputStream());
            boolean bool=minioService.uploadByUpdatedAPI(files.getOriginalFilename(), bytes);

            if(bool)
                response=new Response(HttpStatus.CREATED,Status.SUCCESS, bool, Collections.emptyList());
            else
                response=new Response(HttpStatus.INTERNAL_SERVER_ERROR, Status.ERROR, bool, Collections.emptyList());
            return ResponseEntity.ok(response);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
        }

        response=new Response(HttpStatus.INTERNAL_SERVER_ERROR, Status.ERROR, false, Collections.emptyList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/file/{bucketName}/{fileName}")
    public ResponseEntity<Response> deleteFile(HttpServletRequest request,
                                                      @PathVariable("bucketName") String bucketName, @PathVariable("fileName")String fileName)
    {
        Response response;
        try
        {
            boolean bool=minioService.deleteFile(bucketName, fileName);
            response=new Response(HttpStatus.OK, Status.SUCCESS, bool, Collections.emptyList());

            return ResponseEntity.ok(response);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
        }

        response=new Response(HttpStatus.INTERNAL_SERVER_ERROR, Status.ERROR, false, Collections.emptyList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("generate-pdf")
    public ResponseEntity<Response> generatePdf()
    {
        boolean result=pdfService.generateDummyPdf();
        Response response=new Response(HttpStatus.OK, Status.SUCCESS, result, Collections.emptyList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("nested-json-mapping")
    public ResponseEntity<Response> mapNestedJson(@Valid @RequestBody MultilevelModel model, BindingResult result)
    {
        log.info(model);

        return ResponseEntity.ok(new Response(model));
    }
}
