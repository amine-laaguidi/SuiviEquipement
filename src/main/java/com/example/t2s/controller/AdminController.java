package com.example.t2s.controller;

import com.example.t2s.certificat.Certificat;
import com.example.t2s.certificat.CertificatService;
import com.example.t2s.equipement.Equipement;
import com.example.t2s.equipement.EquipementService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final EquipementService equipementService;
    private final CertificatService certificatService;
    @GetMapping()
    public String admin(){
        return "admin/admin";
    }
    @GetMapping("ajouterEqui")
    public String addEquipement(Model model){
        model.addAttribute("equipement",new Equipement());

        return "admin/ajouterEqui";
    }
    @PostMapping("ajouterEqui")
    public String addEquipement(Model model, @ModelAttribute("equipement") Equipement equipement,
                                @RequestParam("numCertif") String numCertif,
                                @RequestParam("valable") @DateTimeFormat(pattern = "yyyy-MM-dd")Date date,
                                @RequestParam("certif")MultipartFile multipartFile){

        try {
            if(certificatService.numeroExist(numCertif))
                throw new Exception("Numéro de certificat déja utilisé");
            Equipement equi = equipementService.save(equipement);
            Certificat certificat = new Certificat();
            certificat.setDate(date);
            certificat.setNumero(numCertif);
            certificat.setActive(false);
            equi.addCertificat(certificat);
            Long idC = certificatService.save(certificat,multipartFile).getIdC();
            equipementService.save(equi);
            certificatService.utiliser(idC,equi);
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
        }
        model.addAttribute("equipement",new Equipement());
        return "admin/ajouterEqui";
    }
    @GetMapping("equipements")
    public String equipements(Model model){
        try {
            model.addAttribute("equipements",equipementService.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "admin/equipements";
    }
}
