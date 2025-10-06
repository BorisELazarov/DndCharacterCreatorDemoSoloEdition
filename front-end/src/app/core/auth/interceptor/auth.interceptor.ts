import { HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../../services/auth-service/auth.service';
import { inject } from '@angular/core';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  let authService:AuthService=inject(AuthService);
  req=req.clone({
    headers:authService.getAuthHeaders(req.headers)
  });
  return next(req);
};
