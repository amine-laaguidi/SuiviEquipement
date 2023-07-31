package com.example.t2s.controller;

import com.example.t2s.accessoire.Accessoire;
import com.example.t2s.accessoire.AccessoireService;
import com.example.t2s.certificat.Certificat;
import com.example.t2s.certificat.CertificatService;
import com.example.t2s.demande.DemandeReinitService;
import com.example.t2s.equipement.Equipement;
import com.example.t2s.equipement.EquipementService;
import com.example.t2s.utilisateur.User;
import com.example.t2s.utilisateur.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final EquipementService equipementService;
    private final UserService userService;
    private final CertificatService certificatService;
    private final AccessoireService accessoireService;
    @GetMapping()
    public String admin(Model model, HttpSession session) throws Exception {
        if(session.getAttribute("currentUser") == null)
            session.setAttribute("currentUser",userService.getPrincipalUser());
        model.addAttribute("nbrEquipements",equipementService.findAll().stream().count());
        model.addAttribute("nbrAccessoires",accessoireService.findAll().stream().count());
        model.addAttribute("nbrExpired",equipementService.countEquiByEtat(true));
        model.addAttribute("nbrNotExpired",equipementService.countEquiByEtat(false));
        model.addAttribute("equipements",equipementService.findAllOneCertif());
        return "admin/admin";
    }
      //_________________________________________________________________//
     //---------------------------- Equipements-------------------------//
    //_________________________________________________________________//
    @GetMapping("equipements")
    public String equipements(Model model){
        try {
            model.addAttribute("equipements",equipementService.findAllOneCertif());
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
        }
        return "admin/equipements";
    }
    @GetMapping("equipement/details/{idE}")
    public String equipementDetails(Model model,@PathVariable("idE") Long idE){
        try {
            model.addAttribute("equipement",equipementService.findById(idE));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "admin/equipementDetails";
    }
      //_________________________________________________________________//
     //---------------------------- Cértificats-------------------------//
    //_________________________________________________________________//
    @GetMapping("certificats")
    public String certificats(Model model){
        try {
            model.addAttribute("certificatsA",certificatService.findCertificatsByActive(true));
            model.addAttribute("certificatsI",certificatService.findCertificatsByActive(false));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "admin/Certificats";
    }
     //__________________________________________________________________//
     //---------------------------- Accressoires------------------------//
    //_________________________________________________________________//
    @GetMapping("accessoires")
    public String getAccessoires(Model model){
        try {
            model.addAttribute("accessoires",accessoireService.findAll());
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
        }
        return "admin/accessoires";
    }
}
