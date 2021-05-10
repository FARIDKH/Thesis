import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ProductCategoryInterface } from "../interfaces/productCategory.interface";

@Injectable()
export class ProductCategoryService {


    constructor(private httpClient : HttpClient) {}
    baseURL : string = "http://localhost:3000/api"


    readProductCategoryFromDB() : Observable<[ProductCategoryInterface]>  {
        let allPCs = this.httpClient.get<[ProductCategoryInterface]>(
            this.baseURL + "/public/productCategory/getAll", 
            {}
        )
        return allPCs
    }


    addProductCategoryToDB( title: String, productCategory?: Number) : Observable<Object> {
        var data = {title}
        var url = this.baseURL + "/admin/productCategory/create";

        if (productCategory != null) {
            url = url + "/" + productCategory.toString();
        } 
        let x = this.httpClient.post(
            url,
            data,
        )
        return x
    }

}