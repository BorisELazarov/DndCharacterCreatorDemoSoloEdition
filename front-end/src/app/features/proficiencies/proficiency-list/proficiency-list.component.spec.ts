import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProficiencyListComponent } from './proficiency-list.component';

describe('ProficiencyComponent', () => {
  let component: ProficiencyListComponent;
  let fixture: ComponentFixture<ProficiencyListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProficiencyListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProficiencyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
