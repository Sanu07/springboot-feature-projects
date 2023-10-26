# Query.user: User!
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| id |  | ✅ | Int! |
            
## Example
```graphql
{
  user(id: 123456789) {
    id
    name
    country
    profileImage1
    profileImage2
    profileImage3
  }
}

```