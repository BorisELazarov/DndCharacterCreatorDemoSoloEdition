import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailsFeatureComponent } from './details-feature.component';

describe('DetailsFeatureComponent', () => {
  let component: DetailsFeatureComponent;
  let fixture: ComponentFixture<DetailsFeatureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailsFeatureComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DetailsFeatureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
