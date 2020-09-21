# SofQuaExp

#Project
Imagine you want to establish a start-up company focusing on data mining and analysis of various
kinds of data. First, you want to develop a prototype of a software tool that would enable
automation of the data analysis process. The data analysis process consists of following steps:
1. Parsing of the input options
2. Retrieval of a dataset from repository.
3. Data filtering & analysis
4. Output of results

The prototype is going to have only a limited functionality now but it will serve as a basis for
future development. It will be essential for your company business, therefore the quality of
architecture and the code is already very important.
The prototype will focus on analysis of simple datasets containing information about eshop orders.
For the individual process steps, the prototype should support following features:

1. The tool will be executed from CMD with following parameters:
java -jar da-tool.jar -d [DATASET LOCATION] -m [MANIPULATION METHODS]
-o [OUTPUT TYPE] [OUTPUT FILE]

        • DATASET LOCATION will be a path to remote or local CSV file.

        • MANIPULATION METHODS will contain list of methods names that are going to be applied to the data. The list can contain both filtering and analytical methods. The methods are executed in the provided order.

        • OUTPUT TYPE specifies type of output. The prototype should support either JSON, XML or plain text with corresponding values for this argument - ’json’, ’xml’, ’plain’.

        • OUTPUT FILE is the path of the output file.

2. The dataset will be represented by a single csv file with the following structure:
order id,order date,customer email,customer address,total price,order status
A sample dataset is available at:
https://is.muni.cz/el/fi/jaro2020/PV260/um/seminars/java_groups/initial_
assignment/orders_data.csv
For the prototype, it is enough to support at least retrieval of csv file from local and
remote location.

3. There are two main types of methods for data manipulation. First, the data filtering methods
take a dataset as input, then they modify the dataset (e.g. by removing invalid entries,
removing duplicates, data normalization, etc. ) and they output a modified dataset. Second,
the analytical methods take dataset as an input and they output results of their analysis,
which can be a number, string etc. Multiple methods can be applied to a dataset in a single
execution of the tool.
For the data filtration methods, the prototype should implement methods for removing
items with empty customer’s email or empty customer’s address. For analytical
methods, the prototype should be able to determine the average price of orders per
year (separately for paid and unpaid orders), the total price of orders per year
(only paid ones) and the top 3 customers with the highest number of orders.

4. The results of the analytical methods are stored in specified output format. The prototype
should support at least one of the following: xml, json, plain text.






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
* -d https://is.muni.cz/el/fi/jaro2020/PV260/um/seminars/java_groups/initial_assignment/orders_data.csv -m average:2018 -o plain output_print.txt
* -d output_data.csv -m empty_customer -o xml output_print.xml
