angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app/api/v1';

    $scope.fillTable = function (pageIndex = 1) {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                id: $scope.filter ? $scope.filter.id : null,
                title: $scope.filter ? $scope.filter.title : null,
                min_cost: $scope.filter ? $scope.filter.min_cost : null,
                max_cost: $scope.filter ? $scope.filter.max_cost : null,
                page: pageIndex
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;
            let minPageIndex = pageIndex - 2;
            if (minPageIndex < 1) {
                minPageIndex = 1;
            }

            let maxPageIndex = pageIndex + 2;
            if (maxPageIndex > $scope.ProductsPage.totalPages) {
                maxPageIndex = $scope.ProductsPage.totalPages;
            }

            $scope.PaginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);
        });
    };
    $scope.fillTable();


    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        };
        return arr
    };

    $scope.productFindById = function () {
        var id = document.getElementById('elem1').value
        $http.get(contextPath + '/products/' + id)
            .then(function (response) {
                $scope.productId = response.data;
            });
    };

    $scope.editProduct = function () {
        $http.put(contextPath + '/products', $scope.product)
            .then(function (response) {
                $scope.product = null;
                $scope.fillTable();
            });
    };

    $scope.deleteProductById = function (productId) {
        $http.delete(contextPath + '/products/' + productId)
            .then(function (response) {
                $scope.fillTable();
            });
    };

    $scope.createNewProduct = function () {
        $http.post(contextPath + '/products', $scope.product)
            .then(function (response) {
                $scope.product = null;
                $scope.fillTable();
            });
    };

    $scope.addBasket = function (product) {
        $http.post(contextPath + '/basket/', product )
            .then(function (response){
                $scope.basketProduct = response.data
                var s = response.data, sum = 0;
                for(var i=0; i < s.length; i ++){
                    sum += s[i].sumCost;
                }
                $scope.totalCost = sum;
            });
    };

    $scope.incrementCount  = function (id){
        $http.get(contextPath + '/basket/inc/'+ id)
            .then(function (response){
                    $scope.basketProduct = response.data
                var s = response.data, sum = 0;
                for(var i=0; i < s.length; i ++){
                    sum += s[i].sumCost;
                }
                $scope.totalCost = sum;
            });
    }

    $scope.decrimentCount = function (id){
        $http.get(contextPath + '/basket/dec/'+ id)
            .then(function (response){
                $scope.basketProduct = response.data
                var s = response.data, sum = 0;
                for(var i=0; i < s.length; i ++){
                    sum += s[i].sumCost;
                }
                $scope.totalCost = sum;
            });
    }

    $scope.deleteProductFromBasket = function (id){
        $http.get(contextPath + '/basket/del/'+ id)
            .then(function (response){
                $scope.basketProduct = response.data
                var s = response.data, sum = 0;
                for(var i=0; i < s.length; i ++){
                    sum += s[i].sumCost;
                }
                $scope.totalCost = sum;
            });
    }

    $scope.createOrder = function (){
        var userName = $scope.user.username;
        $http.get(contextPath + '/basket/order/' + userName )
            .then(function (response){

            });
    }

    $scope.tryToAuth = function () {
        $http.post('http://localhost:8189/app/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    var userName = $scope.user.username;
                    $scope.user.password = null;
                    $scope.authorized = true;
                    $scope.fillTable();
                }
            }, function errorCallback(response) {
                window.alert("Error");
            });
    };
});