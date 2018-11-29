var express = require('express');
var app = express();
let jarHandler = require('./src/JarHandler');

/**
 * Error handler
 */
app.use(function (err, req, res, next) {
    res.status(500).send({
        error: err.message
    })
});

// Respond when all 3 parameters are given
app.get('/pickup/:pickup/dropoff/:dropoff/passengers/:passengers', function (req, res) {
    let pickup = req.params.pickup;
    let dropoff = req.params.dropoff;
    let passengers = req.params.passengers;
    /**
     * Start the JAR process and wait for the result
     */
    jarHandler(pickup, dropoff, passengers).then((result) => {
        // Return the result
        res.send(result);
    }, (error) => {
        // Something went wrong
        res.status(500);
        res.send({
            error: error
        });
    });
});


// Respond with path not found otherwise
app.get('/*', function (req, res) {
    res.status(400);
    res.send({
        error: 'Path does not exist'
    })
});

app.listen(8080);