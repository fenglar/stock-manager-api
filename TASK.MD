# Mentoring - Spring Security + Keycloak

The idea behind this task will be to secure our app using keycloak + spring security to validate if user has proper privilege to access the endpoint. 
Also, as an extra task we could add the keycloak + spring security on order-manager-api and add a service to service token authentication, so all calls from
order-manager-api to stock-manager-api will be secure by token. 

## Steps:

- Create a Keycloak server
- Configure some users - 2 is enough I guess
- Create 2 roles: One to read stock, another to update stock
- Assign one user for both roles, and another user for only read stock
- Configure stock app to use spring security and connect with keycloak
- Configure swagger to connect with keycloak
- Add the proper priv for read and update stock endpoint
- Test it with proper privs

EXTRA:
- Configure spring-security on order service
- On the endpoint to call stock service add the service token - service to service token