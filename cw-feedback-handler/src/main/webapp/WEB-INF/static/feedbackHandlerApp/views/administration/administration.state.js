'use strict';

/**
 * Created by Emanuel Stoeckli on 02.06.15.
 */
angular.module('FeedbackApp')
    .config(function($stateProvider, VIEWS_DIR) {
        $stateProvider
            .state('tabs.administration', {
                url: '/administration',
                controller: 'AdministrationCtrl',
                templateUrl: VIEWS_DIR + 'administration/template.html',
                data: {
                  'selectedTab': 1
                }
        });
  });
