import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditProficiencyComponent } from './edit-proficiency.component';

describe('EditProficiencyComponent', () => {
  let component: EditProficiencyComponent;
  let fixture: ComponentFixture<EditProficiencyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditProficiencyComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditProficiencyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
