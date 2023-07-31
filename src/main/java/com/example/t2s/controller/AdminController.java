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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final EquipementService equipementService;
    private final UserService userService;
    private final CertificatService certificatService;
    private final AccessoireService accessoireService;
    private final DemandeReinitService demandeReinitService;
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
            model.addAttribute("success","Equipement ajouté avec succès");
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
        }
        return addEquipement(model);
    }
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
    @GetMapping("equipement/delete/{idE}")
    public String equipementDelete(Model model,@PathVariable("idE")Long idE,@RequestParam(defaultValue = "") String page){
        try {
            equipementService.deleteById(idE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(page.equals("dash"))
            return "redirect:/admin";
        return "redirect:/admin/equipements";
    }
      //_________________________________________________________________//
     //---------------------------- Cértificats-------------------------//
    //_________________________________________________________________//
    @GetMapping("ajouterCert")
    public String addCertificat(Model model)  {
        try {
            model.addAttribute("equipements",equipementService.findAll());
            model.addAttribute("certificat",new Certificat());
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
        }
        return "admin/ajouterCert";
    }
    @PostMapping("ajouterCert")
    public String addCertificat(Model model ,@RequestParam("numCertif") String numCertif,
                                @RequestParam("valable") @DateTimeFormat(pattern = "yyyy-MM-dd")Date date,
                                @RequestParam("equipement") Long equipement,
                                @RequestParam("certif")MultipartFile multipartFile){

        try {
            if(certificatService.numeroExist(numCertif))
                throw new Exception("Numéro de certificat déja utilisé");
            Certificat certificat = new Certificat();
            certificat.setNumero(numCertif);
            certificat.setDate(date);
            Equipement equi = equipementService.findById(equipement);
            Certificat cert = certificatService.findCertificatByEquipementIdEAndActive(equipement,true).get();
            Long idC;
            if(cert.getDate().compareTo(certificat.getDate()) <= 0){
                certificat.setActive(false);
                equi.addCertificat(certificat);
                 idC = certificatService.save(certificat,multipartFile).getIdC();
                equipementService.save(equi);
                certificatService.utiliser(idC,equi);
            }else{
                certificat.setActive(false);
                equi.addCertificat(certificat);
                certificatService.save(certificat,multipartFile);
                equipementService.save(equi);
            }
            model.addAttribute("success","Cértificat ajouté avec succès");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error",e.getMessage());
        }
        return addCertificat(model);
    }
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
    @GetMapping("utiliserCertif/{idC}")
    public String utiliserCertif(Model model,@PathVariable("idC") Long idC,@RequestParam(defaultValue = "") String page){
        Certificat certificat = null;
        try{
            certificat = certificatService.findById(idC).get();
            certificatService.utiliser(idC,certificat.getEquipement());
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
        }
        if(page.equals("equiDetails"))
            return "redirect:/admin/equipement/details/"+certificat.getEquipement().getIdE().toString();
        return certificats(model);
    }
    @GetMapping("certificat/delete/{idC}")
    public String deleteCertificat(Model model,@PathVariable("idC")Long idC,@RequestParam(defaultValue = "") String page){
        Certificat certificat = null;
        try {
            certificat = certificatService.findById(idC).get();
            if(certificat.getActive())
                throw new Exception("impossible de supprimer ce cértificat");
            certificatService.deleteById(idC);
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
        }
        if(page.equals("equiDetails"))
            return "redirect:/admin/equipement/details/"+certificat.getEquipement().getIdE().toString();
        return "redirect:/admin/certificats";
    }
     //__________________________________________________________________//
     //---------------------------- Accressoires------------------------//
    //_________________________________________________________________//
    @GetMapping("ajouterAcce")
    public String addAccessoire(Model model)  {
        try {
            model.addAttribute("equipements",equipementService.findAll());
            model.addAttribute("accessoire",new Accessoire());
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
        }
        return "admin/ajouterAcce";
    }
    @PostMapping("ajouterAcce")
    public String addAccessoire(Model model,@ModelAttribute("accessoire") Accessoire accessoire,
                                @RequestParam("equipement") Long equipement)  {
        try {
            if(accessoireService.refExist(accessoire.getRef()))
                throw new Exception("Référence déja utilisé");
            Equipement equi = equipementService.findById(equipement);
            equi.addAccessoire(accessoire);
            accessoireService.save(accessoire);
            equipementService.save(equi);
            model.addAttribute("success","Accessoire ajouté avec succès");
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
        }
        return addAccessoire(model);
    }
    @GetMapping("accessoires")
    public String getAccessoires(Model model){
        try {
            model.addAttribute("accessoires",accessoireService.findAll());
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
        }
        return "admin/accessoires";
    }
    @GetMapping("accessoire/delete/{idA}")
    public String deleteAccessoire(Model model,@PathVariable("idA")Long idA,@RequestParam(defaultValue = "") String page){
        Accessoire accessoire=null;
        try {
            accessoire = accessoireService.findById(idA).get();
            accessoireService.deleteById(idA);
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
        }
        if(page.equals("equiDetails"))
            return "redirect:/admin/equipement/details/"+accessoire.getEquipement().getIdE().toString();
        return "redirect:/admin/accessoires";
    }
      //_________________________________________________________________//
     //---------------------------- Utilisateurs------------------------//
    //_________________________________________________________________//
    @GetMapping("utilisateurs")
    public String utilisateurs(Model model){
        try {
            model.addAttribute("users",userService.findAll());
            model.addAttribute("demandesReinit",demandeReinitService.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "/admin/utilisateurs";
    }
    @GetMapping("utilisateur/enable/{email}/{enable}")
    public String enableUser(@PathVariable String email,@PathVariable int enable){
        try {
            User user = userService.findById(email).get();
            if(enable==1)
                user.setEnabled(true);
            else
                user.setEnabled(false);
            userService.update(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/utilisateurs";
    }
    @GetMapping("/utilisateur/role/{email}/{role}")
    public String roleUser(@PathVariable String email,@PathVariable String role){
        try {
            User user = userService.findById(email).get();
            user.setRole(role);
            userService.update(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/utilisateurs";
    }
    @GetMapping("/utilisateur/delete/{email}")
    public String deleteUser(@PathVariable String email){
        try {
           userService.deleteById(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/utilisateurs";
    }
    @GetMapping("/demandeReinit/delete/{id}")
    public String deleteDemande(@PathVariable Long id) throws Exception {
        demandeReinitService.deleteById(id);
        return "redirect:/admin/utilisateurs";
    }
}
