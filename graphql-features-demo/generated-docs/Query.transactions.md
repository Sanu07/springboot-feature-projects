# Query.transactions: [Transaction]
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| folioId |  | Optional | Int |
            
## Example
```graphql
{
  transactions(folioId: 123456789) {
    id
    folioId
    amount
  }
}

```