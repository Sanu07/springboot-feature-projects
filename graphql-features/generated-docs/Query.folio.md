# Query.folio: Folio
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| id |  | ✅ | Int! |
            
## Example
```graphql
{
  folio(id: 123456789) {
    id
    folioNumber
  }
}

```