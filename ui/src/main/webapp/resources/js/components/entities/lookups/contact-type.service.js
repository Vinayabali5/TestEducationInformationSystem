/**
 * This is the factory definition for the ContactType Data Service. This defines how to handle data about ContactTypeService objects.
 *
 * Applied Styles: [Y001, Y002, Y010, Y021, Y022, Y023, Y024, Y050, Y051, Y052, Y053]
 *
 * @type Data Service
 */

(function() {
    'use strict';

    angular
        .module('ContactTypeService', ['ui-notification'])
        .factory('ContactType', contactTypeFactory);

    contactTypeFactory.$inject = ['$q', '$http', '$rootScope', 'GLOBAL', 'Notification'];

    function contactTypeFactory($q, $http, $rootScope, GLOBAL, Notification) {
        /* jshint validthis:true */
        var url = GLOBAL.API + '/contactTypes/';
        //Public Interface
        var factory = {
            query: getAll,
            get: getById,
            create: create,
            save: save
        };

        return factory;

        //Private Interface
        /**
         * This method is used to retrieve all the ContactType from the API collection.
         *
         * @return {ContactTypeService} An array of ContactType objects.
         */
        function getAll() {
            var deferred = $q.defer();
            $http.get(url).then(function(response) {
                deferred.resolve(response);
                $rootScope.$emit('contact-types-loaded', response.data);
            }, function(response) {
                deferred.reject(response);
            });
            return deferred.promise;
        }

        /**
         * This method is used to retrieve an instance of a ContactType from the API collection.
         * @param  {int} id of the ContactType object that is to be retrieved.
         * @return {ContactTypeService} An ContactType object as identified by the id.
         */
        function getById(id) {
            var deferred = $q.defer();
            if (id != undefined && id != null) {
                $http.get(url + id).then(function(response) {
                    deferred.resolve(response);
                    $rootScope.$emit('contact-types-loaded', response.data);
                }, function(response) {
                    deferred.reject(response);
                });
            } else {
                deferred.reject({
                    message: "No ID Supplied"
                });
            }
            return deferred.promise;
        }

        /**
         * This method is used to create a new instance of an ContactType object in the database by POSTing the
         * required data to the API.
         *
         * @param  {ContactType} contactType An ContactType object to persist to the database.
         * @param  {Function} callback (Optional) A function to be called on a successful call to the API
         * @return {various} This method will return different things depending on the success or failure of the API call.
         * On a success full call the data returned from the API is returned, in the event of an error on the API call the
         * status and data are returned, if no ContactType data is provided then the method returns null.
         */
        function create(contactType, callback) {
            var deferred = $q.defer();
            if (contactType != undefined && contactType != null) {
                $http.post(url, contactType).then(function(response) {
                    deferred.resolve(response);
                    $rootScope.$emit('contact-type-saved', response.data);
                    if (callback) {
                        callback();
                    }
                }, function(response) {
                    deferred.reject(response);
                    Notification.error("Error:" + response.data.message);
                });
            } else {
                deferred.reject({
                    message: "No ContactType ID Supplied"
                });
            }
            return deferred.promise;
        }

        /**
         * This method is used to save changes to an existing ContactType object.
         *
         * @param  {ContactType} contactType An ContactType object with the data to be updated.
         * @param  {Function} callback (Optional) A function to be called on a successful call to the API
         * @return {various} This method will return different things depending on the success or failure of the API call.
         * On a success full call the data returned from the API is returned, in the event of an error on the API call the
         * status and data are returned, if no ContactType data is provided then the method returns null.
         */
        function save(contactType, callback) {
            var deferred = $q.defer();
            $http.put(url + contactType.id, contactType).then(function(response) {
                deferred.resolve(response);
                $rootScope.$emit('contact-type-saved', response.data);
                if (callback) {
                    callback();
                }
            }, function(response) {
                deferred.reject(response);
                Notification.error("Error:" + response.data.message);
            });
            return deferred.promise;
        }
    }
})();
