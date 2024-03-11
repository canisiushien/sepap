/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.entities.Document;
import com.mfptps.appdgessddi.service.dto.DocumentDTO;
import com.mfptps.appdgessddi.utils.ResponseMessage;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface DocumentService {

    ResponseMessage create(DocumentDTO documentDTO, MultipartFile documentFile);

    Document get(Long id);

    Resource download(String fileUri);

    Page<Document> findAll(Pageable pageable);

    void delete(Long id);
}
