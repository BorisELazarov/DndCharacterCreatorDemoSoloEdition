import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { LocalStorageService } from '../../../services/local-storage-service/local-storage.service';

export const userGuard: CanActivateFn = (route, state) => {
  const localStorageService:LocalStorageService=inject(LocalStorageService);
  if(localStorageService.getItem("role")==='user'
    && localStorageService.getItem("deleted")=='false'){
      return true;
  }
  const router:Router=inject(Router);
  router.navigateByUrl('home');
  return false;
};