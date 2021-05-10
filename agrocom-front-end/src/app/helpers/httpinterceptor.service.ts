import { HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AuthService } from "../auth/auth.service";


@Injectable()
export class HttpInterceptorService implements HttpInterceptor {


    constructor(private authService: AuthService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (this.authService.isUserLoggedIn() && req.url.indexOf("basicauth") === -1 ){
            const authReq = req.clone({
                headers : new HttpHeaders({
                    // 'Content-Type' : 'application/json',
                    'Authorization' : "Bearer " + this.authService.getToken()
                })
            })
            console.log("HttpInterceptorService")
            // console.log(authReq)
            return next.handle(authReq)
        } else {
            return next.handle(req)
        }
    }
    
}