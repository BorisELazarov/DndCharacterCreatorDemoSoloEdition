import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FeatureListDeletedComponent } from './feature-list-deleted.component';

describe('FeatureListDeletedComponent', () => {
  let component: FeatureListDeletedComponent;
  let fixture: ComponentFixture<FeatureListDeletedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FeatureListDeletedComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FeatureListDeletedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
