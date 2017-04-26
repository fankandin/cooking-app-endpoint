# business-datasource-manufacturer

[![build status](https://gitlab.regis24.de/developer/business-datasource-manufacturer/badges/master/build.svg)](https://gitlab.regis24.de/developer/business-datasource-manufacturer/commits/master)
[![coverage report](https://gitlab.regis24.de/developer/business-datasource-manufacturer/badges/master/coverage.svg)](https://gitlab.regis24.de/developer/business-datasource-manufacturer/commits/master)

A web service managing the manufacturers in our system.

## Web service

Live swagger docs are available at `/swagger-ui.html`.

### Usage
 
The application relies on the standard spring boot mechanism.

### Configuration options

Outside of the usual configuration options, the following parameters are required / avaliable:

##### valid_product_types

Type: `string`

Required: `true`

A comma-separated string of AnfrageArts, which are allowed to be set for a manufacturer.

##### default_tax_class_id

Type: `integer`

Required: `true`

The default tax class ID. Should be set to `2`.
