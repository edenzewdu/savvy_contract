//package com.contract.security;
//
//import com.contract.entity.*;
//import com.contract.jsf.EmployeesController;
//import com.contract.jsf.util.JsfUtil;
//import jakarta.ejb.EJB;
//import jakarta.el.ELContext;
//import jakarta.enterprise.context.SessionScoped;
//import jakarta.faces.application.FacesMessage;
//import jakarta.faces.context.ExternalContext;
//import jakarta.faces.context.FacesContext;
//import jakarta.inject.Inject;
//import jakarta.inject.Named;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import org.primefaces.PrimeFaces;
//
//import java.io.Serializable;
//import java.io.UnsupportedEncodingException;
//import java.security.NoSuchAlgorithmException;
//import java.util.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Named(value = "loginView")
//@SessionScoped
//public class LoginView implements Serializable {
//    private static final long serialVersionUID = 3254181235309041386L;
//    private static Logger log = Logger.getLogger(LoginView.class.getName());
//    // private final UserTableController userTableController;
//
//    @EJB
//    private com.contract.session.AbstractFacadeQuerySavvy ejbFacade1;
//
//    private Integer employeeId;
//    private String password;
//    private String oldPassword;
//
//    private String newPassword;
//    private String confirmPassword;
//
//
//    public String getOldPassword() {
//        return oldPassword;
//    }
//
//    public void setOldPassword(String oldPassword) {
//        this.oldPassword = oldPassword;
//    }
//
//    public String getNewPassword() {
//        return newPassword;
//    }
//
//    public void setNewPassword(String newPassword) {
//        this.newPassword = newPassword;
//    }
//
//    public String getConfirmPassword() {
//        return confirmPassword;
//    }
//
//    public void setConfirmPassword(String confirmPassword) {
//        this.confirmPassword = confirmPassword;
//    }
//
//    public Integer getEmployeeId() {
//        return employeeId;
//    }
//
//    public void setEmployeeId(Integer employeeId) {
//        this.employeeId = employeeId;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String login() throws UnsupportedEncodingException, NoSuchAlgorithmException {
//
//        FacesContext context = FacesContext.getCurrentInstance();
//        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
//        ELContext elContext = context.getELContext();
//        EmployeesController employeesController = (EmployeesController) elContext.getELResolver().getValue(elContext, null, "employeesController");
//
//
//        EmployeesTable employee = employeesController.getEmployeesTable(employeeId);
//        if (employee == null) {
//            JsfUtil.addErrorMessage("Login Failed.");
//            return "signin";
//        }
//
//        user = EmployeesTable.get(0);
//        try {
//            if (AuthenticationUtils.encodeSHA2561(password).equals(user.getPassword())) {
//                log.info("Authentication done for user: " + user.getEmployeesId().getNameFirst());
//
//                if (user.getStatus().equals("Inactive")) {
//                    JsfUtil.addErrorMessage("Your account is inactive, Please contact system administrator.");
//                    return "signin";
//                }
//
//                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//                Map<String, Object> sessionMap = externalContext.getSessionMap();
//                HttpSession session = request.getSession();
//                session.setAttribute("username", user);
//
//                sessionMap.put("User", user);
//                loadUserLinkPrivileges(user);
//
//                if (user.getStatus().equals("New")) {
//                    // return "changePassword";
//                    FacesContext.getCurrentInstance().getExternalContext().redirect("changePassword.xhtml");
//                    return "";
//                } else if (user.getStatus().equals("Inactive")) {
//                    FacesContext.getCurrentInstance().getExternalContext().redirect("inactive.xhtml");
//                    return "";
//                }
//
//                this.password = "";
//
//                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
//                return "";
//            } else {
//                JsfUtil.addErrorMessage("Login Failed.");
//            }
//        } catch (Exception e) {
//            //  ejbFacade.clearingGeneralQueryObjects();
//            e.printStackTrace();
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed!", null));
//            return "signin";
//        }
//        return "signin";
//    }
//
////    @POST
////    @Path("/login")
////    @Consumes(MediaType.APPLICATION_JSON)
////    @Produces(MediaType.APPLICATION_JSON)
////    public HttpConnection.Response login(UserCredentials credentials) {
////        User user = userService.findByUsername(credentials.getUsername());
////
////        if (user == null || !passwordHasher.verify(credentials.getPassword(), user.getHashedPassword())) {
////            return Response.status(Response.Status.UNAUTHORIZED).build();
////        }
////
////        String token = jwtService.generateToken(user);
////        return Response.ok(new AuthResponse(token)).build();
////    }
//
//    private boolean isPasswordExpired(UserTable user) {
//        // Fetch password policy config
//        if (getPasswordStrengthConfig() == null || getPasswordStrengthConfig().getPasswordExpiryDays() == null) {
//            return false; // No expiry policy set
//        }
//
//        int expiryDays = getPasswordStrengthConfig().getPasswordExpiryDays();
//        Date lastPasswordChange = user.getPasswordLastUpdated(); // Assuming a field exists in UserTable
//
//        if (lastPasswordChange == null) {
//            return false; // No last password change recorded
//        }
//
//        Calendar expiryDate = Calendar.getInstance();
//        expiryDate.setTime(lastPasswordChange);
//        expiryDate.add(Calendar.DAY_OF_YEAR, expiryDays);
//
//        return new Date().after(expiryDate.getTime()); // Check if expired
//    }
//
//    private Map<String, String> userLinkPrivileges = new HashMap<>();
//    private Map<String, String> userButtonPrivileges = new HashMap<>();
//
//    public Map<String, String> getUserLinkPrivileges() {
//        return userLinkPrivileges;
//    }
//
//    public void setUserLinkPrivileges(Map<String, String> userLinkPrivileges) {
//        this.userLinkPrivileges = userLinkPrivileges;
//    }
//
//    public Map<String, String> getUserButtonPrivileges() {
//        return userButtonPrivileges;
//    }
//
//    public void setUserButtonPrivileges(Map<String, String> userButtonPrivileges) {
//        this.userButtonPrivileges = userButtonPrivileges;
//    }
//
//    private void loadUserLinkPrivileges(UserTable user) {
//        userLinkPrivileges = new HashMap<>();
//        userButtonPrivileges = new HashMap<>();
//
//        // Fetch user's roles and privileges from DB
//        List<RolePrevilage> roleLinkPrivileges = (List<RolePrevilage>) ejbFacade1.findByIntAndString("RolePrevilage.userPrevilage", "user", "type", user.getId(), "link");
//        List<RolePrevilage> roleButtonPrivileges = (List<RolePrevilage>) ejbFacade1.findByIntAndString("RolePrevilage.userPrevilage", "user", "type", user.getId(), "button");
//
//        // Store in map (key: actual URL, value: link label)
//        for (RolePrevilage rp : roleLinkPrivileges) {
//            PrevilageTable privilege = rp.getPrevilageTableId();
//            userLinkPrivileges.put(privilege.getLink(), privilege.getLinkLable());
//        }
//
//        for (RolePrevilage rp : roleButtonPrivileges) {
//            PrevilageTable privilege = rp.getPrevilageTableId();
//            userButtonPrivileges.put(privilege.getButton(), privilege.getLinkLable());
//        }
//
//    }
//
//    public String logout() {
//        FacesContext context = FacesContext.getCurrentInstance();
//        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
//        ELContext elContext = context.getELContext();
//        // loginManager loginManager = (loginManager) elContext.getELResolver().getValue(elContext, null, "loginManager");
//        //  LoginView loginView = (LoginView) elContext.getELResolver().getValue(elContext, null, "loginView");
//
//        try {
//
//            request.logout();
//            // clear the session
//            ((HttpSession) context.getExternalContext().getSession(false)).invalidate();
//
//            // loginManager.getUsersList().remove(user);
//            this.user = null;
//        } catch (ServletException e) {
//            log.log(Level.SEVERE, "Failed to logout user!", e);
//        }
//        try {
//            return "/signin.xhtml?faces-redirect=true";
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    private PasswordStrengthConfig getPasswordStrengthConfig() {
//        return configService.getItems().get(0);
//    }
//
//    public void changePassword() throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        Employees employee = getAuthenticatedUser().getEmployeesId();
//
//        if(!AuthenticationUtils.encodeSHA2561(oldPassword).equals(getAuthenticatedUser().getPassword())){
//            JsfUtil.addErrorMessage("Error,Current password is incorrect.");
//            return;
//        }
//
//        if (!newPassword.equals(confirmPassword)) {
//            JsfUtil.addErrorMessage("Error,New passwords do not match.");
//            return;
//        }
//
//
//        if(!validatePassword(newPassword, employee)){
//            // JsfUtil.addErrorMessage("Error,Password does not meet the required criteria.");
//            return;
//        }
//
//        try {
//
//            getAuthenticatedUser().setPassword(AuthenticationUtils.encodeSHA2561(newPassword));
//            getAuthenticatedUser().setPasswordLastUpdated(new Date());
//            getAuthenticatedUser().setStatus("Active");
//            userTableController.update(getAuthenticatedUser());
//            addToHistory(AuthenticationUtils.encodeSHA2561(newPassword));
//            //  passwordService.changePassword(employee, newPassword);
//
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Password changed successfully!"));
//
//            // Execute JavaScript to delay the redirection
//            PrimeFaces.current().executeScript("setTimeout(function(){ window.location.href='" +
//                    FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml'; }, 3000);");
//        } catch (RuntimeException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Inject
//    private PasswordStrengthConfigController configService;
//
//    public boolean validatePassword(String password, Employees employee) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        PasswordStrengthConfig config = configService.getItems().get(0);
//
//        if (password.length() < config.getMinLength()) {
//            JsfUtil.addErrorMessage("Password must be at least " + config.getMinLength() + " characters long.");
//            //  throw new RuntimeException("Password must be at least " + config.getMinLength() + " characters long.");
//            return false;
//        }
//
//        if (password.length() > config.getMaxLength()) {
//            JsfUtil.addErrorMessage("Password must not exceed " + config.getMaxLength() + " characters.");
//            //  throw new RuntimeException("Password must not exceed " + config.getMaxLength() + " characters.");
//            return false;
//        }
//
//        if (!hasMinimumUppercase(password, config.getRequiredUppercases())) {
//            JsfUtil.addErrorMessage("Password must contain at least " + config.getRequiredUppercases() + " uppercase letters.");
//            //   throw new RuntimeException("Password must contain at least " + config.getRequiredUppercases() + " uppercase letters.");
//            return false;
//        }
//
//        if (!hasMinimumLowercase(password, config.getRequiredLowercase())) {
//            JsfUtil.addErrorMessage("Password must contain at least " + config.getRequiredLowercase() + " lowercase letters.");
//            return false;
//            //  throw new RuntimeException("Password must contain at least " + config.getRequiredLowercase() + " lowercase letters.");
//        }
//
//        if (!hasMinimumNumbers(password, config.getRequiredNumbers())) {
//            JsfUtil.addErrorMessage("Password must contain at least " + config.getRequiredNumbers() + " numbers.");
//            return false;
//            //throw new RuntimeException("Password must contain at least " + config.getRequiredNumbers() + " numbers.");
//        }
//
//        if (!hasMinimumSpecialChars(password, config.getRequiredSpecials(), config.getSpecialChars())) {
//            JsfUtil.addErrorMessage("Password must contain at least " + config.getRequiredSpecials() + " special characters from " + config.getSpecialChars());
//            return false;
//            //  throw new RuntimeException("Password must contain at least " + config.getRequiredSpecials() + " special characters from " + config.getSpecialChars());
//        }
//
//        if (config.get Size() > 0) {
//
//            if(check (employee, password, config.get Size())){
//                return true;
//            }else{
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private boolean hasMinimumUppercase(String password, int requiredCount) {
//        long count = password.chars().filter(Character::isUpperCase).count();
//        return count >= requiredCount;
//    }
//
//    private boolean hasMinimumLowercase(String password, int requiredCount) {
//        long count = password.chars().filter(Character::isLowerCase).count();
//        return count >= requiredCount;
//    }
//
//    private boolean hasMinimumNumbers(String password, int requiredCount) {
//        long count = password.chars().filter(Character::isDigit).count();
//        return count >= requiredCount;
//    }
//
//    private boolean hasMinimumSpecialChars(String password, int requiredCount, String allowedSpecialChars) {
//        long count = password.chars()
//                .mapToObj(c -> (char) c)
//                .filter(c -> allowedSpecialChars.indexOf(c) != -1)
//                .count();
//        return count >= requiredCount;
//    }
//
//    private boolean check (Employees employee, String newPassword, int historySize) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        String newPasswordHash = AuthenticationUtils.encodeSHA2561(newPassword);
//
//        List<String> pastHashes = ejbFacade1.findByIdReturnStringMaximumLimited(" .findByEmployeeReturnPass",
//                "employee", employee.getId(), historySize);
//
//        if (pastHashes.contains(newPasswordHash)) {
//            JsfUtil.addErrorMessage("You cannot reuse a previous password. Please choose a new one.");
//            return false;
//            //  throw new RuntimeException("You cannot reuse a previous password. Please choose a new one.");
//        }
//        return true;
//    }
//
//
//    private void addToHistory(String newPassword) {
//          ph = new  ();
//        ph.setOldPasswordHash(newPassword);
//        ph.setChangedAt(new Date());
//        ph.setEmployeeId(getAuthenticatedUser().getEmployeesId());
//
//         .save(ph);
//        //  ejbFacade1.create(ph);
//    }
//
//}
