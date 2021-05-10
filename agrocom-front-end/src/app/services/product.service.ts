import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { environment } from "src/environments/environment";
import { AuthService } from "../auth/auth.service";
import { PriceFilter } from "../interfaces/priceFilter.interface";
import { ProductInterface } from "../interfaces/product.interface";


@Injectable()
export class ProductService {

    constructor(
        private authService : AuthService,
        private httpClient : HttpClient,
        private sanitizer : DomSanitizer
        ) {}

    

    createProduct(productForm : FormData){
        this.httpClient.post(
            environment.host + "/products/create/userID/"+ this.authService.getAuthUserId(),
            productForm
        ).subscribe((res) => {
            console.log(res)
        })
    }

    getProductImg(product:ProductInterface) {
        return this.httpClient.get(
            environment.host + "/products/public/getProductImage/" + product.id,
            {responseType : 'blob'}).subscribe(b => {
                if(b.size != 0){
                    let objecturl =  URL.createObjectURL(b)
                    product.imgURL = this.sanitizer.bypassSecurityTrustUrl(objecturl);
                } else {
                    product.imgURL = "/assets/images/default-image.jpg"
                }
            })
    }

    readAllUserProducts() : Observable<[ProductInterface]> {
        return this.httpClient.get<[ProductInterface]>(
            environment.host + "/products/user/" + this.authService.getAuthUserId() + "/getAll"
        )
    }

    readAllProducts() : Observable<[ProductInterface]> {
        return this.httpClient.get<[ProductInterface]>(
            environment.host + "/products/public/getAll"
        );
    }

    readAllProductsByProductCategoryId(pcID: Number) : Observable<ProductInterface[]>{
        return this.httpClient.get<ProductInterface[]>(
            environment.host + "/products/public/productCategoryID/" + pcID + "/getAll"
        );
    }

    getMinAndMaxPrice() : Observable<PriceFilter> {
        return this.httpClient.get<PriceFilter>(
            environment.host + "/products/public/getMinAndMax"
        );
    }

    readAllProductsBetweenMinAndMax(minPrice: Number, maxPrice: Number){
        return this.httpClient.get<ProductInterface[]>(
            environment.host + "/products/public/minPrice/"+minPrice+"/maxPrice/"+maxPrice+"/getAll"
        )
    }


    readProductById(productId: Number){
        return this.httpClient.get<ProductInterface>(
            environment.host + "/products/public/productId/" + productId
        )
    }




}