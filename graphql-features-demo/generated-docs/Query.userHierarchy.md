# Query.userHierarchy: UserHierarchy!
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| id |  | ✅ | Int! |
            
## Example
```graphql
{
  userHierarchy(id: 123456789) {
    id
    name
    country
    profileImage1
    profileImage2
    profileImage3
  }
}

```