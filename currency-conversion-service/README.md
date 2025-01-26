### Command to run the application
```
mvn spring-boot:run
```
Note: you can pass profile as *test* (Optional)

### request
```json
{
  "currencyTo": "INR",
  "currencyFrom": "USD",
  "amount": 100.50,
  "isStoreEmployee": true,
  "isStoreAffiliate": false,
  "isPriorityCustomer": true,
  "hasOnlyGroceries": false
}
```
### Response
```json
{
  "currencyTo": "INR",
  "currencyFrom": "USD",
  "subTotalBeforeDiscount": 100.5,
  "finalAmount": 6072.35,
  "appliedDiscount": 30,
  "discountType": "PERCENTAGE"
}
```
### Sample cURL
```cURL
curl --request POST \
  --url http://localhost:8080/v1/api/calculate \
  --header 'Content-Type: application/json' \
  --data '{
  "currencyTo": "INR",
  "currencyFrom": "USD",
  "amount": 100.50,
  "isStoreEmployee": true,
  "isStoreAffiliate": false,
  "isPriorityCustomer": true,
  "hasOnlyGroceries": false
}
'
```

### Brief explanation on the design
Once the payload is received, it undergoes basic validation checks. If the payload passes these checks, we proceed to calculate the rates. Prior to calling the exchange-rate API, we first check our in-memory cache (Caffeine) to see if the conversion rates are already available. If the data is found in the cache, we retrieve it from there; otherwise, we query the API to fetch the conversion rates and store the result in the cache using a write-through strategy.

After obtaining the conversion rates, we apply the Strategy Design Pattern. The payload is passed through various discount strategies, which evaluate the discount in the appropriate currency format. Finally, we return the maximum discounted value, considering all applicable discount conditions by default.
### What has been implemented
- Functional Requirements are completely implemented with basic validation
- NFR CB++ has not been implemented
- Basic retry and timeout mechanism has been implemented
- Test cases for the different strategies has been implemented
