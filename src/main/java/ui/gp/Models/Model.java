package ui.gp.Models;

import ui.gp.Models.Users.*;
import ui.gp.Database.*;
import ui.gp.View.ViewFactory;

import java.sql.ResultSet;

public class Model {
    private static Model model;
    private final ViewFactory view;
    private final DatabaseConnection databaseConnection;
    private Role loginRole;
    private Dependent dependent;
    private SystemAdmin admin;
    private PolicyOwner policyOwner;
    private PolicyHolder policyHolder;
    private InsuranceManager insuranceManager;
    private InsuranceSurveyor insuranceSurveyor;
    private boolean loginSuccess;

    public Model() {
        this.databaseConnection = DatabaseConnection.getInstance();
        this.view = new ViewFactory(databaseConnection);
        this.dependent = new Dependent("", "", "", Role.Dependent, "", "", "", "");
        this.admin = new SystemAdmin("", "", "", Role.System_Admin, "", "", "", "");
        this.policyOwner = new PolicyOwner("", "", "", Role.Policy_Owner, "", "", "", "");
        this.policyHolder = new PolicyHolder("", "", "", Role.Policy_Holder, "", "", "", "");
        this.insuranceManager = new InsuranceManager("", "", "", Role.Insurance_Manager, "", "", "", "");
        this.insuranceSurveyor = new InsuranceSurveyor("", "", "", Role.Insurance_Surveyor, "", "", "", "");
        this.loginSuccess = false;
    }

    public Dependent getDependent() {
        return dependent;
    }

    public SystemAdmin getAdmin() {
        return admin;
    }

    public PolicyOwner getPolicyOwner() {
        return policyOwner;
    }

    public PolicyHolder getPolicyHolder() {
        return policyHolder;
    }

    public InsuranceManager getInsuranceManager() {
        return insuranceManager;
    }

    public InsuranceSurveyor getInsuranceSurveyor() {
        return insuranceSurveyor;
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewFactory getView() {
        return view;
    }

    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    public Role getLoginRole() {
        return loginRole;
    }

    public boolean getLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginRole(Role loginRole) {
        this.loginRole = loginRole;
    }

    public void evaluateUserCred(String username, String password) {
        Role role = databaseConnection.getUserRole(username);
        ResultSet resultSet = null;
        if (role != null) {
            switch (role) {
                case Dependent:
                    resultSet = databaseConnection.getDependentData(username, password);
                    break;
                case System_Admin:
                   resultSet = databaseConnection.getAdminData(username, password);
                    break;
                case Policy_Owner:
                    resultSet = databaseConnection.getPolicyOwnerData(username, password);
                    break;
                case Policy_Holder:
                    resultSet = databaseConnection.getPolicyHolderData(username, password);
                    break;
                case Insurance_Manager:
                   resultSet = databaseConnection.getInsuranceManagerData(username, password);
                    break;
                case Insurance_Surveyor:
                    resultSet = databaseConnection.getInsuranceSurveyorData(username, password);
                    break;
            }

            if (resultSet != null) {
                try {
                    if (resultSet.isBeforeFirst()) {
                        this.loginRole = role;
                        this.loginSuccess = true;
                        switch (role) {
                            case Dependent:
                                if (resultSet.next())
                                {
                                    String id = resultSet.getString("id");
                                    String fullname = resultSet.getString("fullname");
                                    String email = resultSet.getString("email");
                                    String phoneNumber = resultSet.getString("phonenumber");
                                    String address = resultSet.getString("address");
                                    this.dependent = new Dependent(id, username, password, role, fullname, email, phoneNumber, address);
                                }
                                    break;
                                    case System_Admin:
                                        //Load Admin data
                                        break;
                                    case Insurance_Surveyor:
                                        //Load Surveyor data
                                        break;
                                    case Insurance_Manager:
                                        //Load Manager data
                                        break;
                                    case Policy_Holder:
                                        //Load Policy Holder data
                                        break;
                                    case Policy_Owner:
                                        //Load Policy Owner data
                                        break;
                                }
                        }
                    }catch (Exception e) {
                    e.printStackTrace();
                }
                }
            }
        }
    }