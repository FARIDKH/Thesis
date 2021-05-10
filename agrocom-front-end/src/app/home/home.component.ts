import { Component } from "@angular/core";
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from "@angular/router";
import { AuthService } from "../auth/auth.service";
import { UserInterface } from "../interfaces/user.interface";

@Component({
    selector : 'app-home',
    templateUrl : './home.component.html',
    styleUrls : [
        './home.component.scss'
    ]
})

export class HomeComponent {


    constructor(
        private userAuthService : AuthService,
        private registerFormBuilder : FormBuilder,
        private loginFormBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router
        ){}

    newUser? : UserInterface

    registerForm = this.registerFormBuilder.group({
        firstName : [null,Validators.required],
        lastName : [null, Validators.required],
        email : [null,[Validators.email, Validators.required]],
        password : [null, Validators.required],
        password_again : [null, Validators.required],
        sellerValue : false
    })

    loginForm = this.loginFormBuilder.group({
        email: new FormControl(null,[Validators.required,Validators.email]),
        password: [null, Validators.required],
    })

    
    errorMessages : String[] = []
    showRegister = true

    changeVal() : void {
        this.errorMessages = []
        this.showRegister = !this.showRegister
    }

    registerUser() : void {
        let formData : UserInterface = this.registerForm.value
        if(formData.password != formData.password_again){
            this.errorMessages.push("Passwords doesn't match!")
        } else {
            this.errorMessages = []
            let status = this.userAuthService.registerUserToDB(formData)
            status.subscribe(() => {
                this.router.navigate(['/'])
            })
        }
        
    }
    

    get registerEmail(){
        return this.registerForm.get('email')
    }

    get loginEmail(){
        return this.loginForm.get('email')
    }

    loginUser() : void {
        let formData = this.loginForm
        let email = formData.get(['email'])?.value
        let password = formData.get('password')?.value
        let status = this.userAuthService.authService(email,password)
        this.errorMessages = []
        status.subscribe(() => {
            if(this.userAuthService.isUserLoggedIn()){
                this.router.navigate([''])
            }
        },
        (error) => {
            if(error.error.text != ""){
                this.errorMessages.push(error.error.message)
            }
          })
    }

    


}

