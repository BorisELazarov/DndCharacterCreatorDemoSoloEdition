import { CanActivateFn} from '@angular/router';

export const characterGuard: CanActivateFn = (route, state) => {
  return false;
};
