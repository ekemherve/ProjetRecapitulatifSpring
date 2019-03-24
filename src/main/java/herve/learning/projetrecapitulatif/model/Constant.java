package herve.learning.projetrecapitulatif.model;

public class Constant {

    public static final String USER_ALREADY_EXISTS = "UserAlreadyExists";
    public static final String USER_IS_NULL = "UserIsNull";
    public static final String USER_DOESNT_EXIST = "UserDoNotExists";
    public static final String USERNAME_OR_EMAIL_ALREADY_EXIST = "UserOrEmailAlreadyExist";
    public static final String USER_NOT_AUTHENTICATED = "UserNotAuthenticated!!!";

    public static final String ROLE_ALREADY_EXISTS = "RoleAlreadyExists";
    public static final String ROLE_NOT_EXISTS = "RoleDoNotExists";
    public static final String Role_IS_NULL = "RoleIsNull";


    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public static final String SIGNING_KEY = "Herve";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "scopes";



}
