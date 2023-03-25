package com.unb.taap.controller;

import com.unb.taap.core.singleton.TAAPManager;
import com.unb.taap.model.LabSession;
import com.unb.taap.model.TokenValidation;
import com.unb.taap.model.UserType;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LabController {

  @RequestMapping(
      value = {"/register-lab"},
      method = RequestMethod.POST)
  public String registerLabSession(
      @RequestParam("labSessionName") String labSessionName, Model model) {
    LabSession labSession = TAAPManager.getInstance().createLabSession(labSessionName);
    model.addAttribute("userToken", labSession.getUserToken());
    model.addAttribute("taToken", labSession.getTaToken());
    return "index";
  }

  @RequestMapping(
      value = {"/join-lab"},
      method = RequestMethod.POST)
  public String joinLab(
      @RequestParam("name") String name,
      @RequestParam("id") String id,
      @RequestParam("token") String token,
      @RequestParam("seat") String seat,
      HttpSession session) {
    session.setAttribute("name", name);
    session.setAttribute("id", id);
    session.setAttribute("token", token);
    session.setAttribute("seat", seat);
    TokenValidation tokenValidation = TAAPManager.getInstance().validateToken(token);
    if (tokenValidation.isValid()) {
      TAAPManager.getInstance()
          .registerParticipant(
              name, id, seat, token, tokenValidation.getUserType(), tokenValidation.getLabID());
      session.setAttribute("loggedInUser", tokenValidation.getUserType().toString());
      session.setAttribute("labID", tokenValidation.getLabID());
      LabSession labSession = TAAPManager.getInstance().getLabSession(tokenValidation.getLabID());
      session.setAttribute("labName", labSession.getLabName());
      if (UserType.TA.equals(tokenValidation.getUserType())) {
        session.setAttribute("userToken", labSession.getUserToken());
      }
    }
    return "redirect:/";
  }

  @RequestMapping("/exit")
  public String exit(HttpSession session) {
    session.invalidate();
    return "redirect:/";
  }
}
