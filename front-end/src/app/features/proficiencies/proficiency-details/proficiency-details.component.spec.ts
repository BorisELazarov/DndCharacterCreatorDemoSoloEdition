import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProficiencyDetailsComponent } from './proficiency-details.component';

describe('ProficiencyDetailsComponent', () => {
  let component: ProficiencyDetailsComponent;
  let fixture: ComponentFixture<ProficiencyDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProficiencyDetailsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProficiencyDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
