# Query.bookInputValidator: Book
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| input |  | Optional | BookInput |
            
## Example
```graphql
{
  bookInputValidator(input: {id : 123456789, name : "randomString"}) {
    id
    name
    isbn
  }
}

```