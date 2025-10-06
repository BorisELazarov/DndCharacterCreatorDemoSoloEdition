import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { dataManagerGuard } from './data-manager.guard';

describe('dataManagerGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => dataManagerGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
