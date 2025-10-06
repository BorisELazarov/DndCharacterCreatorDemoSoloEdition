import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { characterGuard } from './character.guard';

describe('characterGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => characterGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
