package com.example.t2s.utilisateur;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @GetMapping
    public String index() throws Exception {
        User user = userService.getPrincipalUser();
        if(user!= null && user.getRole().equals("ROLE_USER"))
            return "redirect:/user";
        if(user!= null && user.getRole().equals("ROLE_ADMIN"))
            return "redirect:/admin";
        return "redirect:/login";
    }
    @GetMapping("login")
    public String login(Model model)  {
        try {
            if( userService.getPrincipal() !=null)
                return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
        }
        return "auth/login";
    }
    @GetMapping("register")
    public String register(Model model){
        model.addAttribute("user",new User());
        try {
            if( userService.getPrincipal() !=null)
                return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
        }
        return "auth/register";
    }
    @PostMapping("register")
    public String register(@ModelAttribute("userRegidtry") User user,
                           @RequestParam("passwordRepeat") String passwordRepeat,Model model) {
        if(!user.getPassword().equals(passwordRepeat)){
            model.addAttribute("error","mot de passe de confirmartion incorrect");
            model.addAttribute("user",user);
            return "auth/register";
        }
        else
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userService.save(user);
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            model.addAttribute("user",user);
            return "auth/register";
        }
        model.addAttribute("success","Inscription avec succès, attender la confirmation de l'administrateur");
        return login(model);
    }
    @GetMapping("user")
    public String user(){
        return "user/user";
    }

}
