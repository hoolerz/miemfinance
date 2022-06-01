# MFinance Backend

### Startup process:
- (\*...* means your info)

##### 0. The following components must be installed:
  - Java Development Kit
  - IntelliJ IDEA Ultimate with lombok plugin
  - PostgreSQL
  - Git
#####  1. Сlone project repository
##### 2. Checkout *dev* branch
##### 3. Start infrastructure locally in docker
You can start application via docker compose (that skips step 5). In Terminal in IntelliJ IDEA from *miemfinance* folder:  
`docker-compose up`

* now you can work with files locally
* now you can work with db without its installation
* now you can upload and download files via minio

##### 4. Add PostgreSQL Data Source in tab *Database* in IntelliJ IDEA
##### 5. Compile and run the project
##### 6. Obtain JWT auth token
1. Go to [Microsoft login page](https://login.microsoftonline.com/common/oauth2/v2.0/authorize?response_type=code&client_id=e0298532-643b-40af-a866-a309039151ea&scope=openid%20email%20profile&state=a6Ko0MHIIeC1YS2iiGPePc6f5v_N0D_hl9qP54Q5tKw%3D&redirect_uri=miem-invest://oauth/callback&nonce=T79MpglFnbgplf4_Kqmk8tHyJkjhbJ6AMlG2xwDizSU) with network tab
2. Login into your account
3. Copy recieved authentication code
4. Send following request
###
POST https://localhost:8443/api/login/callback?code=\*yourCode\
Accept: application/json
Content-Type: application/json
5. Recieve your token in the response body
##### 7. Test API via Postman

After initial start up it is recommended to change environment variable MIEMFINANCE_APP_INITIALIZATION to false.
