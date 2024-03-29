package es.unex.asee.mercapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.google.gson.Gson
import es.unex.asee.mercapp.api.MercadonaAPI
import es.unex.asee.mercapp.data.api.*
import es.unex.asee.mercapp.data.model.GenericCategory
import es.unex.asee.mercapp.data.model.Product
import es.unex.asee.mercapp.data.model.User
import es.unex.asee.mercapp.database.dao.CategoryDAO
import es.unex.asee.mercapp.database.dao.UserDao

class Repository (private val userDao: UserDao,
                  private val categoryDAO: CategoryDAO,
                  private val networkService: MercadonaAPI
) {


    private val userFilter = MutableLiveData<Long>()

    val productsInCart: LiveData<List<Product>> =
        userFilter.switchMap{ userid -> categoryDAO.getProductsFromUserCart(userid) }


    suspend fun addProductToCart(product: Product, userId: Long) {
        categoryDAO.insertProductInUserCart(userId, product.productId)
        Log.v("HTTP", "addProductToCart: " + product.toString() + " " + userId)
    }

    suspend fun deleteProductFromCart(product: Product, userId: Long) {
        categoryDAO.deleteProductFromUserCart(userId, product.productId)
        Log.v("HTTP", "deleteProductFromCart: " + product.toString() + " " + userId)
    }

    suspend fun fetchUserByName(name: String): User {
        var user = User(null)
        try {
            user = userDao.findByName(name) ?: User(null)
        } catch (cause: Throwable) {
            throw Error("Unable to fetch data from API", cause)
        }
        return user
    }

    suspend fun insertUser(user: User): Long {
        var id: Long = -1
        try {
            id = userDao.insert(user)
        } catch (cause: Throwable) {
            throw Error("Unable to fetch data from API", cause)
        }
        return id
    }

    fun setUserid(userid: Long) {
        userFilter.value = userid
    }

    suspend fun fetchSuperCategories(): List<GenericCategory> {
        try {
            var categories = categoryDAO.getSuperCategories()

            return categories
        } catch (cause: Throwable) {
            throw Error("Unable to fetch data from API", cause)
        }
    }

     suspend fun apiFetchSuperCategories() {
        try {
            //fetch supercategorias de la api
            //val superCategoryPage = networkService.getCategories()
            //no funciona

            val gson = Gson()
            val superCategoryPageJson = """ {"next":null,"count":26,"results":[{"id":12,"name":"Aceite, especias y salsas","order":7,"layout":2,"published":true,"categories":[{"id":112,"name":"Aceite, vinagre y sal","order":7,"layout":1,"published":true,"is_extended":false},{"id":115,"name":"Especias","order":7,"layout":1,"published":true,"is_extended":false},{"id":116,"name":"Mayonesa, ketchup y mostaza","order":7,"layout":1,"published":true,"is_extended":false},{"id":117,"name":"Otras salsas","order":7,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":18,"name":"Agua y refrescos","order":8,"layout":2,"published":true,"categories":[{"id":156,"name":"Agua","order":8,"layout":1,"published":true,"is_extended":false},{"id":163,"name":"Isotónico y energético","order":8,"layout":1,"published":true,"is_extended":false},{"id":158,"name":"Refresco de cola","order":8,"layout":1,"published":true,"is_extended":false},{"id":159,"name":"Refresco de naranja y de limón","order":8,"layout":1,"published":true,"is_extended":false},{"id":161,"name":"Tónica y bitter","order":8,"layout":1,"published":true,"is_extended":false},{"id":162,"name":"Refresco de té y sin gas","order":8,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":15,"name":"Aperitivos","order":9,"layout":2,"published":true,"categories":[{"id":135,"name":"Aceitunas y encurtidos","order":9,"layout":1,"published":true,"is_extended":false},{"id":133,"name":"Frutos secos y fruta desecada","order":9,"layout":1,"published":true,"is_extended":false},{"id":132,"name":"Patatas fritas y snacks","order":9,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":13,"name":"Arroz, legumbres y pasta","order":10,"layout":2,"published":true,"categories":[{"id":118,"name":"Arroz","order":10,"layout":1,"published":true,"is_extended":false},{"id":121,"name":"Legumbres","order":10,"layout":1,"published":true,"is_extended":false},{"id":120,"name":"Pasta y fideos","order":10,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":9,"name":"Azúcar, caramelos y chocolate","order":11,"layout":2,"published":true,"categories":[{"id":89,"name":"Azúcar y edulcorante","order":11,"layout":1,"published":true,"is_extended":false},{"id":95,"name":"Chicles y caramelos","order":11,"layout":1,"published":true,"is_extended":false},{"id":92,"name":"Chocolate","order":11,"layout":1,"published":true,"is_extended":false},{"id":97,"name":"Golosinas","order":11,"layout":1,"published":true,"is_extended":false},{"id":90,"name":"Mermelada y miel","order":11,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":24,"name":"Bebé","order":14,"layout":2,"published":true,"categories":[{"id":216,"name":"Alimentación infantil","order":14,"layout":1,"published":true,"is_extended":false},{"id":219,"name":"Biberón y chupete","order":14,"layout":1,"published":true,"is_extended":false},{"id":218,"name":"Higiene y cuidado","order":14,"layout":1,"published":true,"is_extended":false},{"id":217,"name":"Toallitas y pañales","order":14,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":19,"name":"Bodega","order":15,"layout":2,"published":true,"categories":[{"id":164,"name":"Cerveza","order":15,"layout":1,"published":true,"is_extended":false},{"id":166,"name":"Cerveza sin alcohol","order":15,"layout":1,"published":true,"is_extended":false},{"id":181,"name":"Licores","order":15,"layout":1,"published":true,"is_extended":false},{"id":174,"name":"Sidra y cava","order":15,"layout":1,"published":true,"is_extended":false},{"id":168,"name":"Tinto de verano y sangría","order":15,"layout":1,"published":true,"is_extended":false},{"id":170,"name":"Vino blanco","order":15,"layout":1,"published":true,"is_extended":false},{"id":173,"name":"Vino lambrusco y espumoso","order":15,"layout":1,"published":true,"is_extended":false},{"id":171,"name":"Vino rosado","order":15,"layout":1,"published":true,"is_extended":false},{"id":169,"name":"Vino tinto","order":15,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":8,"name":"Cacao, café e infusiones","order":16,"layout":2,"published":true,"categories":[{"id":86,"name":"Cacao soluble y chocolate a la taza","order":16,"layout":1,"published":true,"is_extended":false},{"id":81,"name":"Café cápsula y monodosis","order":16,"layout":1,"published":true,"is_extended":false},{"id":83,"name":"Café molido y en grano","order":16,"layout":1,"published":true,"is_extended":false},{"id":84,"name":"Café soluble y otras bebidas","order":16,"layout":1,"published":true,"is_extended":false},{"id":88,"name":"Té e infusiones","order":16,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":3,"name":"Carne","order":17,"layout":2,"published":true,"categories":[{"id":46,"name":"Arreglos","order":17,"layout":1,"published":true,"is_extended":false},{"id":38,"name":"Aves y pollo","order":17,"layout":1,"published":true,"is_extended":false},{"id":47,"name":"Carne congelada","order":17,"layout":1,"published":true,"is_extended":false},{"id":37,"name":"Cerdo","order":17,"layout":1,"published":true,"is_extended":false},{"id":42,"name":"Conejo y cordero","order":17,"layout":1,"published":true,"is_extended":false},{"id":43,"name":"Embutido","order":17,"layout":1,"published":true,"is_extended":false},{"id":44,"name":"Hamburguesas y picadas","order":17,"layout":1,"published":true,"is_extended":false},{"id":40,"name":"Vacuno","order":17,"layout":1,"published":true,"is_extended":false},{"id":45,"name":"Empanados y elaborados","order":17,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":7,"name":"Cereales y galletas","order":18,"layout":2,"published":true,"categories":[{"id":78,"name":"Cereales","order":18,"layout":1,"published":true,"is_extended":false},{"id":80,"name":"Galletas","order":18,"layout":1,"published":true,"is_extended":false},{"id":79,"name":"Tortitas","order":18,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":4,"name":"Charcutería y quesos","order":159,"layout":2,"published":true,"categories":[{"id":48,"name":"Aves y jamón cocido","order":159,"layout":1,"published":true,"is_extended":false},{"id":52,"name":"Bacón y salchichas","order":159,"layout":1,"published":true,"is_extended":false},{"id":49,"name":"Chopped y mortadela","order":159,"layout":1,"published":true,"is_extended":false},{"id":51,"name":"Embutido curado","order":159,"layout":1,"published":true,"is_extended":false},{"id":50,"name":"Jamón serrano","order":159,"layout":1,"published":true,"is_extended":false},{"id":58,"name":"Paté y sobrasada","order":159,"layout":1,"published":true,"is_extended":false},{"id":54,"name":"Queso curado, semicurado y tierno","order":159,"layout":1,"published":true,"is_extended":false},{"id":56,"name":"Queso lonchas, rallado y en porciones","order":159,"layout":1,"published":true,"is_extended":false},{"id":53,"name":"Queso untable y fresco","order":159,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":17,"name":"Congelados","order":213,"layout":2,"published":true,"categories":[{"id":147,"name":"Arroz y pasta","order":213,"layout":1,"published":true,"is_extended":false},{"id":148,"name":"Carne","order":213,"layout":1,"published":true,"is_extended":false},{"id":154,"name":"Helados","order":213,"layout":1,"published":true,"is_extended":false},{"id":155,"name":"Hielo","order":213,"layout":1,"published":true,"is_extended":false},{"id":150,"name":"Marisco","order":213,"layout":1,"published":true,"is_extended":false},{"id":149,"name":"Pescado","order":213,"layout":1,"published":true,"is_extended":false},{"id":151,"name":"Pizzas","order":213,"layout":1,"published":true,"is_extended":false},{"id":884,"name":"Rebozados","order":213,"layout":2,"published":true,"is_extended":false},{"id":152,"name":"Tartas y churros","order":213,"layout":1,"published":true,"is_extended":false},{"id":145,"name":"Verdura","order":213,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":14,"name":"Conservas, caldos y cremas","order":215,"layout":2,"published":true,"categories":[{"id":122,"name":"Atún y otras conservas de pescado","order":215,"layout":1,"published":true,"is_extended":false},{"id":123,"name":"Berberechos y mejillones","order":215,"layout":1,"published":true,"is_extended":false},{"id":127,"name":"Conservas de verdura y frutas","order":215,"layout":1,"published":true,"is_extended":false},{"id":130,"name":"Gazpacho y cremas","order":215,"layout":1,"published":true,"is_extended":false},{"id":129,"name":"Sopa y caldo","order":215,"layout":1,"published":true,"is_extended":false},{"id":126,"name":"Tomate","order":215,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":21,"name":"Cuidado del cabello","order":238,"layout":2,"published":true,"categories":[{"id":201,"name":"Acondicionador y mascarilla","order":238,"layout":1,"published":true,"is_extended":false},{"id":199,"name":"Champú","order":238,"layout":1,"published":true,"is_extended":false},{"id":203,"name":"Coloración cabello","order":238,"layout":1,"published":true,"is_extended":false},{"id":202,"name":"Fijación cabello","order":238,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":20,"name":"Cuidado facial y corporal","order":241,"layout":2,"published":true,"categories":[{"id":192,"name":"Afeitado y cuidado para hombre","order":241,"layout":1,"published":true,"is_extended":false},{"id":189,"name":"Cuidado corporal","order":241,"layout":1,"published":true,"is_extended":false},{"id":185,"name":"Cuidado e higiene facial","order":241,"layout":1,"published":true,"is_extended":false},{"id":191,"name":"Depilación","order":241,"layout":1,"published":true,"is_extended":false},{"id":188,"name":"Desodorante","order":241,"layout":1,"published":true,"is_extended":false},{"id":187,"name":"Gel y jabón de manos","order":241,"layout":1,"published":true,"is_extended":false},{"id":186,"name":"Higiene bucal","order":241,"layout":1,"published":true,"is_extended":false},{"id":190,"name":"Higiene íntima","order":241,"layout":1,"published":true,"is_extended":false},{"id":194,"name":"Manicura y pedicura","order":241,"layout":1,"published":true,"is_extended":false},{"id":196,"name":"Perfume y colonia","order":241,"layout":1,"published":true,"is_extended":false},{"id":198,"name":"Protector solar y aftersun","order":241,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":23,"name":"Fitoterapia y parafarmacia","order":303,"layout":2,"published":true,"categories":[{"id":213,"name":"Fitoterapia","order":303,"layout":1,"published":true,"is_extended":false},{"id":214,"name":"Parafarmacia","order":303,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":1,"name":"Fruta y verdura","order":304,"layout":2,"published":true,"categories":[{"id":27,"name":"Fruta","order":304,"layout":1,"published":true,"is_extended":false},{"id":28,"name":"Lechuga y ensalada preparada","order":304,"layout":1,"published":true,"is_extended":false},{"id":29,"name":"Verdura","order":304,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":6,"name":"Huevos, leche y mantequilla","order":371,"layout":2,"published":true,"categories":[{"id":77,"name":"Huevos","order":371,"layout":1,"published":true,"is_extended":false},{"id":72,"name":"Leche y bebidas vegetales","order":371,"layout":1,"published":true,"is_extended":false},{"id":75,"name":"Mantequilla y margarina","order":371,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":26,"name":"Limpieza y hogar","order":372,"layout":2,"published":true,"categories":[{"id":226,"name":"Detergente y suavizante ropa","order":372,"layout":1,"published":true,"is_extended":false},{"id":237,"name":"Estropajo, bayeta y guantes","order":372,"layout":1,"published":true,"is_extended":false},{"id":241,"name":"Insecticida y ambientador","order":372,"layout":1,"published":true,"is_extended":false},{"id":234,"name":"Lejía y líquidos fuertes","order":372,"layout":1,"published":true,"is_extended":false},{"id":235,"name":"Limpiacristales","order":372,"layout":1,"published":true,"is_extended":false},{"id":233,"name":"Limpiahogar y friegasuelos","order":372,"layout":1,"published":true,"is_extended":false},{"id":231,"name":"Limpieza baño y WC","order":372,"layout":1,"published":true,"is_extended":false},{"id":230,"name":"Limpieza cocina","order":372,"layout":1,"published":true,"is_extended":false},{"id":232,"name":"Limpieza muebles y multiusos","order":372,"layout":1,"published":true,"is_extended":false},{"id":229,"name":"Limpieza vajilla","order":372,"layout":1,"published":true,"is_extended":false},{"id":243,"name":"Menaje y conservación de alimentos","order":372,"layout":1,"published":true,"is_extended":false},{"id":238,"name":"Papel higiénico y celulosa","order":372,"layout":1,"published":true,"is_extended":false},{"id":239,"name":"Pilas y bolsas de basura","order":372,"layout":1,"published":true,"is_extended":false},{"id":244,"name":"Utensilios de limpieza y calzado","order":372,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":22,"name":"Maquillaje","order":419,"layout":2,"published":true,"categories":[{"id":206,"name":"Bases de maquillaje y corrector","order":419,"layout":1,"published":true,"is_extended":false},{"id":207,"name":"Colorete y polvos","order":419,"layout":1,"published":true,"is_extended":false},{"id":208,"name":"Labios","order":419,"layout":1,"published":true,"is_extended":false},{"id":210,"name":"Ojos","order":419,"layout":1,"published":true,"is_extended":false},{"id":212,"name":"Pinceles y brochas","order":419,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":2,"name":"Marisco y pescado","order":439,"layout":2,"published":true,"categories":[{"id":32,"name":"Marisco","order":439,"layout":1,"published":true,"is_extended":false},{"id":34,"name":"Pescado congelado","order":439,"layout":1,"published":true,"is_extended":false},{"id":31,"name":"Pescado fresco","order":439,"layout":1,"published":true,"is_extended":false},{"id":36,"name":"Salazones y ahumados","order":439,"layout":1,"published":true,"is_extended":false},{"id":789,"name":"Sushi","order":439,"layout":2,"published":true,"is_extended":false}],"is_extended":false},{"id":25,"name":"Mascotas","order":440,"layout":2,"published":true,"categories":[{"id":222,"name":"Gato","order":440,"layout":1,"published":true,"is_extended":false},{"id":221,"name":"Perro","order":440,"layout":1,"published":true,"is_extended":false},{"id":225,"name":"Otros","order":440,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":5,"name":"Panadería y pastelería","order":512,"layout":2,"published":true,"categories":[{"id":65,"name":"Bollería de horno","order":512,"layout":1,"published":true,"is_extended":false},{"id":66,"name":"Bollería envasada","order":512,"layout":1,"published":true,"is_extended":false},{"id":69,"name":"Harina y preparado repostería","order":512,"layout":1,"published":true,"is_extended":false},{"id":59,"name":"Pan de horno","order":512,"layout":1,"published":true,"is_extended":false},{"id":60,"name":"Pan de molde y otras especialidades","order":512,"layout":1,"published":true,"is_extended":false},{"id":62,"name":"Pan tostado y rallado","order":512,"layout":1,"published":true,"is_extended":false},{"id":64,"name":"Picos, rosquilletas y picatostes","order":512,"layout":1,"published":true,"is_extended":false},{"id":68,"name":"Tartas y pasteles","order":512,"layout":1,"published":true,"is_extended":false},{"id":71,"name":"Velas y decoración","order":512,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":16,"name":"Pizzas y platos preparados","order":594,"layout":2,"published":true,"categories":[{"id":897,"name":"Listo para Comer","order":594,"layout":1,"published":true,"is_extended":false},{"id":138,"name":"Pizzas","order":594,"layout":1,"published":true,"is_extended":false},{"id":140,"name":"Platos preparados calientes","order":594,"layout":1,"published":true,"is_extended":false},{"id":142,"name":"Platos preparados fríos","order":594,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":11,"name":"Postres y yogures","order":595,"layout":2,"published":true,"categories":[{"id":105,"name":"Bífidus","order":595,"layout":1,"published":true,"is_extended":false},{"id":110,"name":"Flan y natillas","order":595,"layout":1,"published":true,"is_extended":false},{"id":111,"name":"Gelatina y otros postres","order":595,"layout":1,"published":true,"is_extended":false},{"id":106,"name":"Postres de soja","order":595,"layout":1,"published":true,"is_extended":false},{"id":103,"name":"Yogures desnatados","order":595,"layout":1,"published":true,"is_extended":false},{"id":109,"name":"Yogures griegos","order":595,"layout":1,"published":true,"is_extended":false},{"id":108,"name":"Yogures líquidos","order":595,"layout":1,"published":true,"is_extended":false},{"id":104,"name":"Yogures naturales y sabores","order":595,"layout":1,"published":true,"is_extended":false},{"id":107,"name":"Yogures y postres infantiles","order":595,"layout":1,"published":true,"is_extended":false}],"is_extended":false},{"id":10,"name":"Zumos","order":790,"layout":2,"published":true,"categories":[{"id":99,"name":"Fruta variada","order":790,"layout":1,"published":true,"is_extended":false},{"id":100,"name":"Melocotón y piña","order":790,"layout":1,"published":true,"is_extended":false},{"id":143,"name":"Naranja","order":790,"layout":1,"published":true,"is_extended":false},{"id":98,"name":"Tomate y otros sabores","order":790,"layout":1,"published":true,"is_extended":false}],"is_extended":false}],"previous":null}  """.trimIndent()

            // Convertir el JSON en un objeto SuperCategoryPage
            val superCategoryPage: SuperCategoryPage = gson.fromJson(superCategoryPageJson, SuperCategoryPage::class.java)

            Log.v("HTTP", "superCategoryPage: " + superCategoryPage.toString())
            val supercategories = superCategoryPage.SuperCategories
            Log.v("HTTP", "supercategories: " + supercategories.toString())
            val supercategoriesList = supercategories.map { it.toGenericCategory() }
            Log.v("HTTP", "supercategoriesList: " + supercategoriesList.toString())

            //se insertan las supercategorias
            categoryDAO.insertAllCategories(supercategoriesList)
            //para cada suoercategoria se insertan sus categorias y crossref
            for (supercategory in supercategories) {
                val categories = supercategory.categories
                val categoriesList = categories.map { it.toGenericCategory() }
                for (category in categoriesList) {

                    categoryDAO.insertCategoryAndRelate(category, supercategory.toGenericCategory())
                }
            }

        } catch (cause: Throwable) {
            Log.e("HTTP", "Error fetching supercategories", cause)
            throw Error("Unable to fetch data from API", cause)
        }
    }


    suspend fun fetchCategories(superCategory: GenericCategory): List<GenericCategory>  {
        try {
            return categoryDAO.getCategories( superCategory.GenCatId)
        } catch (cause: Throwable) {
            throw Error("Unable to fetch data from API", cause)
        }
    }

    private suspend fun areSubcategoriesInDB( category: GenericCategory): Boolean {
        try {

           if (categoryDAO.getSubCategories( category.GenCatId) == null) {
               return false
           }
            return true

        } catch (cause: Throwable) {
            throw Error("Unable to fetch data from API", cause)
        }
    }

    suspend fun fetchSubCategories(category: GenericCategory): List<GenericCategory>  {
        try {
            var subcatbd : List<GenericCategory> = emptyList()

            //se comprueba si estan ya esa subcateogria y sus productos en la bd, si estan se devuelven sino se piden a la api

            if (areSubcategoriesInDB(category)) {
                subcatbd = networkService.getSubCategories(category.GenCatId).categories.map { it.toGenericCategory() }

                for (subcat in subcatbd) {
                    categoryDAO.insertSubCategoryAndRelate(subcat, category)
                }
            }
            else{
                subcatbd = categoryDAO.getSubCategories(category.GenCatId)
            }

            return subcatbd
        } catch (cause: Throwable) {
            throw Error("Unable to fetch data from API", cause)
        }
    }

    private suspend fun areProductsInDB( subCategory: GenericCategory): Boolean {
        try {

            Log.v("HTTP", "areProductsInDB: " + categoryDAO.getProducts( subCategory.GenCatId).toString())
            if (categoryDAO.getProducts( subCategory.GenCatId).isEmpty()) {
                return false
            }
            return true

        } catch (cause: Throwable) {
            throw Error("Unable to fetch data from API", cause)
        }
    }

    suspend fun fetchProducts(subCategory: GenericCategory): List<Product>  {
        try {
            var products: List<Product> = emptyList()

            if(areProductsInDB(subCategory)){
                products = categoryDAO.getProducts(subCategory.GenCatId)
            }
            else{
                val subcategories = networkService.getSubCategories(fetchCategoryFromSubcategory(subCategory).GenCatId).categories
                Log.v("HTTP", "subcategories: " + subcategories.toString())
                for (apiSubcategory in subcategories) {
                    if (apiSubcategory.id == subCategory.GenCatId) {
                        val productsInfo = apiSubcategory.products
                        Log.v("HTTP", "productsInfo: " + productsInfo.toString())
                        products = productsInfo.map { it.toProduct() }
                        for (product in products) {
                            categoryDAO.insertProductAndRelate(product, subCategory)
                        }
                    }
                }
            }
            Log.v("HTTP", "products: " + products.toString())
            return products
        } catch (cause: Throwable) {
            throw Error("Unable to fetch data from API", cause)
        }
    }

    suspend fun fetchCategoryFromSubcategory(subCategory: GenericCategory): GenericCategory  {
        try {
            return categoryDAO.getCategoryFromSubCategory(subCategory.GenCatId)
        } catch (cause: Throwable) {
            throw Error("Unable to fetch data from API", cause)
        }
    }

    suspend fun fetchSuperCategoryFromCategory(category: GenericCategory): GenericCategory  {
        try {
            return categoryDAO.getSuperCategoryFromCategory(category.GenCatId)
        } catch (cause: Throwable) {
            throw Error("Unable to fetch data from API", cause)
        }
    }

    companion object {
        private const val MIN_TIME_FROM_LAST_FETCH_MILLIS: Long = 3000
    }

}