# Query.offsetPagination: [User!]!
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| limit |  | ✅ | Int! |
| offset |  | ✅ | Int! |
            
## Example
```graphql
{
  offsetPagination(limit: 123456789, offset: 123456789) {
    id
    name
    country
    profileImage1
    profileImage2
    profileImage3
  }
}

```