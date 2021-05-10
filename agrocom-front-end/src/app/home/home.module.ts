
import { CommonModule } from "@angular/common"
import {NgModule} from "@angular/core"
import {ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HomeComponent } from "./home.component"
import { HttpClientModule, HttpHeaders, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthService } from "../auth/auth.service";
import { HttpInterceptorService } from "../helpers/httpinterceptor.service";


@NgModule({
	declarations: [HomeComponent],
    imports : [CommonModule,FormsModule, ReactiveFormsModule, HttpClientModule
    ],
    exports: [HomeComponent],
    providers : [
        AuthService,
        { provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorService, multi: true }
    ]
})

export class HomeModule {}


