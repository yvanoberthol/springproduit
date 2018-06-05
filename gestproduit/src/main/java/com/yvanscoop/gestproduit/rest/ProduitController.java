package com.yvanscoop.gestproduit.rest;

import com.google.zxing.WriterException;
import com.yvanscoop.gestproduit.domain.Produit;
import com.yvanscoop.gestproduit.dto.ProduitDto;
import com.yvanscoop.gestproduit.mail.MailConfig;
import com.yvanscoop.gestproduit.qrcode.QrCodeReader;
import com.yvanscoop.gestproduit.qrcode.QrCodeWriter;
import com.yvanscoop.gestproduit.repository.ProduitRepository;
import com.yvanscoop.gestproduit.service.ProduitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing produitDto.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/")
public class ProduitController {

  private final Logger log = LoggerFactory.getLogger(ProduitController.class);

  @Autowired
  private ProduitService produitService;

  @Autowired
  @Qualifier("sendMailer")
  private MailConfig mailConfig;

  //@Autowired
  //private produitDtoRepository produitDtoRepository;

  /**
   * POST  /produitDtos : Create a new produitDto.
   *
   * @param produit the produitDto to create
   * @return the ResponseEntity with status 201 (Created) and with body the new produitDto, or with status 400 (Bad Request) if the produitDto has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @RequestMapping(value="/produits",method=RequestMethod.POST,produces = MediaType.ALL_VALUE)
  public ResponseEntity<ProduitDto> createproduitDto(@Valid @RequestBody Produit produit) throws URISyntaxException {
    log.debug("REST request to save produitDto : {}", produit);

    ProduitDto result = produitService.save(produit);
    return new ResponseEntity<ProduitDto>(result, HttpStatus.CREATED);
  }


  @RequestMapping(value="/produits/max",method=RequestMethod.GET)
  public int getLast() throws URISyntaxException {
    log.debug("REST request to get last id of product");
    int result = produitService.getLast();
    return result;
  }

  /**
   * PUT  /produitDtos : Updates an existing produitDto.
   *
   * @param produit the produitDto to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated produitDto,
   * or with status 400 (Bad Request) if the produitDto is not valid,
   * or with status 500 (Internal Server Error) if the produitDto couldnt be updated
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @RequestMapping(value="/produits",method=RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<ProduitDto> updateproduit(@Valid @RequestBody Produit produit) throws URISyntaxException {
    log.debug("REST request to update produitDto : {}", produit);
    if (produit.getId() == 0) {
      return createproduitDto(produit);
    }
    ProduitDto result = produitService.update(produit);
    return new ResponseEntity<ProduitDto>(result, HttpStatus.CREATED);
  }

    @RequestMapping(value="/mail",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void mail(@RequestParam(defaultValue = "yvanoberthol@gmail.com") String from,@RequestParam(defaultValue = "yvanoberthol@gmail.com") String to,
                     @RequestParam(defaultValue = "un petit mail") String sujet,@RequestParam(defaultValue = "bonjour") String body){
        log.debug("send mail");
        mailConfig.sendMail(from,to,sujet,body);
    }


  /**
   * GET  /produitDtos : get all the produitDtos.
   *
   * @return the ResponseEntity with status 200 (OK) and the list of produitDtos in body
   */
  @RequestMapping(value="/produits",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Page<Produit> getproduits(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5")int size) {
    log.debug("REST request to get produitDtos");
    return produitService.findAll(new PageRequest(page,size));
  }

  /**
   * GET  /produitDtos/:id : get the "id" produitDto.
   *
   * @param id the id of the produitDto to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body the produitDto, or with status 404 (Not Found)
   */
  @RequestMapping(value="/produit/{id}",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<ProduitDto> getproduit(@PathVariable Long id) {
    log.debug("REST request to get produitDto : {}", id);
    ProduitDto produitDto = produitService.findOne(id);

    return Optional.ofNullable(produitDto)
      .map(result -> new ResponseEntity<>(
        result,
        HttpStatus.OK))
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

  }

    @RequestMapping(value="/produit/qrWriter/{code}",method=RequestMethod.GET)
    public void setQrCode(@PathVariable String code, @RequestParam(defaultValue = "yvan") String text, @RequestParam(defaultValue = "200") int width,
                            @RequestParam(defaultValue = "200")int height,@RequestParam(defaultValue = "C:\\gestproduit\\angularProduit\\src\\assets\\imgs\\imgs.png") String filePath)
            throws WriterException, IOException {
        Produit p = produitService.findByCode(code);
        text = p.getDesignation();
        filePath =  "C:\\gestproduit\\angularProduit\\src\\assets\\imgs\\produit_"+p.getId()+".png";
        QrCodeWriter qrWriter = new QrCodeWriter();
        qrWriter.generateQRCodeImage(text,width,height,filePath);
        p.setQrcode(filePath);
        produitService.save(p);
    }

    @RequestMapping(value="/produit/qrReader",method=RequestMethod.GET)
    public String getQrCode(File image) throws IOException {
        QrCodeReader qrCodeReader = new QrCodeReader();
        return qrCodeReader.decodeQRCode(image);
    }
/*  @RequestMapping (value="/produit/qr/{id}", method = RequestMethod.GET)
  public HttpEntity<byte[]> qr(@PathVariable Long id) {
    byte[] bytes = QRCode.from(produitService.findOne(id)
            .toString()).withSize(120, 120).stream().toByteArray();
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_PNG);
    headers.setContentLength(bytes.length);
    return new ResponseEntity<byte[]> (bytes, headers, HttpStatus.CREATED);
  }*/
  /**
   * DELETE  /produitDtos/:id : delete the "id" produitDto.
   *
   * @param id the id of the produitDto to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @RequestMapping(value="/produit/{id}",method=RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> deleteproduit(@PathVariable Long id) {
    log.debug("REST request to delete produitDto : {}", id);
    produitService.delete(id);
    return new ResponseEntity<ProduitDto>(HttpStatus.NO_CONTENT);
  }

}
