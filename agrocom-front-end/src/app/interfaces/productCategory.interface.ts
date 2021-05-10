
export interface ProductCategoryInterface {
    id : Number
    title : String,
    productCategory? : Number


}

export class ProductCategory implements ProductCategoryInterface {
    id : Number 
    title : String = ""
    productCategory? : Number 

    constructor(id : Number, title: String, productCategory? : Number){
        this.id = id
        this.title = title
        this.productCategory = productCategory
    }

    

}