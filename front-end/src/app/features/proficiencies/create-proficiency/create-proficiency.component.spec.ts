import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateProficiencyComponent } from './create-proficiency.component';

describe('CreateProficiencyComponent', () => {
  let component: CreateProficiencyComponent;
  let fixture: ComponentFixture<CreateProficiencyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateProficiencyComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateProficiencyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
