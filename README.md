# SofQuaExp

## How to use:
    
    Usage: da-tool [-hV] [-d=<datasetLocation>] [-o=<outputType>]
                   [-m=<manipulationMethods>...]... <outputFile>
                   
    Analysis of simple datasets containing information about eshop orders
          <outputFile>        Path of the output file.
      -d=<datasetLocation>    DATASET LOCATION : path to remote or local CSV file
                                with dataset
      -h, --help              Show this help message and exit.
      -m=<manipulationMethods>...
                              Contain list of methods names that are going to be
                                applied to the data. The list can contain both
                                filtering and analytical methods. The methods are
                                executed in the provided order.
      -o=<outputType>         Specifies type of output. The prototype should
                                support either JSON,XML or plain text with
                                corresponding values for this argument - ’json’, ’
                                xml’, ’plain’.
      -V, --version           Print version information and exit.

### parameters:
##### basic:
* <b>empty_customer:</b> methods for removing items with empty customer’s email or empty customer’s address.
* <b>average:</b> the average price of orders per year (separately for paid and unpaid orders).
* <b>total:</b> the total price of orders per year (only paid ones).
* <b>top3:</b> the top 3 customers with the highest number of orders.
##### advance:
* <b>method:year,year,...</b> : get method for specific year.
* <b>example:</b> -m total:2018,2019

## example:
* -d https://is.muni.cz/el/fi/jaro2020/PV260/um/seminars/java_groups/initial_assignment/orders_data.csv -m average:2018 -o plain output_print
* -d output_data.csv -m empty_customer -o plain output_print
