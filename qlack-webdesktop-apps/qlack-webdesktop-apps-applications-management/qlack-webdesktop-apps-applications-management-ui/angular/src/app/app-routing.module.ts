import {RouterModule} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';

// Configuration of Router with all available AppConstants.Routes.
export const routing: ModuleWithProviders = RouterModule.forRoot([
    {
      path: '',
      redirectTo: '/applications',
      pathMatch: 'full'
    },
    {
      path: 'applications',
      loadChildren: () => import('./applications/applications.module').then(m => m.ApplicationsModule)
    },
    {
      path: 'users',
      loadChildren: () => import('./users/users.module').then(m => m.UsersModule)
    },
    {
      path: 'usergroup',
      loadChildren: () => import('./user-group/user-group.module').then(m => m.UserGroupModule)
    },
  ],
  {
    enableTracing: false,
    useHash: true
  });
