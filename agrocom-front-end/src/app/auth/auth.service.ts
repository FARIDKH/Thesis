import { HttpClient, HttpHeaders, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from 'rxjs/operators';
import { AuthInterface } from "../interfaces/auth.interface";
import { UserInterface } from "../interfaces/user.interface";

@Injectable()
export class AuthService {



    SESS_USERNAME_ATTR_NAME = "authUser"
    SESS_USERID_ATTR_NAME = "authId"
    SESS_TOKEN_ATTR_NAME = "authToken"
    SESS_ROLES_ATTR_NAME = "authRoles"


    public username : string = "";
    public password : string = "";
    public user_id : Number = 0;
    private token = ""
    private roles : [string] = [""]

    baseURL = "http://localhost:3000/api"

    constructor(private httpClient : HttpClient) {}
 
    authService(username : string, password : string)  {

        const headers = new HttpHeaders(
            {
                authorization : this.createToken(username,password) 
            }
        ) 

        const resp =  this.httpClient.post<AuthInterface>("http://localhost:3000/api/user/login",
            {username,password},
            {headers}
        )

        return resp.pipe(
            map(((res : AuthInterface) => {
                this.user_id = res.id
                console.log(res)
                this.token =  res.token
                this.username = username;
                this.password = password;
                this.registerLoginInSession();
            }))
        )
    }

    createToken(username : string, password: string) {
        return "Basic " + btoa(username + ":" + password)
    }

    registerUserToDB(user: UserInterface) {
        
        const headers = new HttpHeaders(
            {
                authorization : this.createToken(user.email,user.password) 
            }
        ) 

        let postRequest = this.httpClient.post<AuthInterface>(
            this.baseURL + "/user/registration",
            user,
            {headers}
            )
        return postRequest.pipe(
                map(((res : AuthInterface) => {
                    console.log(res)
                    this.token = res.token
                    this.user_id = res.id
                    this.username = user.email;
                    this.password = user.password;
                    this.registerLoginInSession();
                }))
            )
    }

    

    registerLoginInSession(){
        sessionStorage.setItem(this.SESS_TOKEN_ATTR_NAME,this.token)
        sessionStorage.setItem(this.SESS_USERID_ATTR_NAME,this.user_id.toString())
        sessionStorage.setItem(this.SESS_ROLES_ATTR_NAME,this.roles.toString())
    }

    logout() {
        sessionStorage.removeItem(this.SESS_TOKEN_ATTR_NAME)
        sessionStorage.removeItem(this.SESS_USERID_ATTR_NAME)
        sessionStorage.removeItem(this.SESS_ROLES_ATTR_NAME)
        this.username = ""
        this.password = ""
        this.user_id = 0
        this.token = ""
        this.roles = [""];
    }

    isUserLoggedIn(){
        let user = sessionStorage.getItem(this.SESS_TOKEN_ATTR_NAME)
        if (user == null) return false
        return true
    }

    getToken(){
        return sessionStorage.getItem(this.SESS_TOKEN_ATTR_NAME)
    }

    getAuthUserId(){
        return sessionStorage.getItem(this.SESS_USERID_ATTR_NAME)
    }

    getAuthUser(){

        

        return this.httpClient.get<AuthInterface>(
            this.baseURL + "/user"
        );
        
    }

   

    


}