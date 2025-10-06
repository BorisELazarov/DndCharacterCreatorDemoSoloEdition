import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProficiencyDeletedListComponent } from './proficiency-deleted-list.component';

describe('ProficiencyDeletedListComponent', () => {
  let component: ProficiencyDeletedListComponent;
  let fixture: ComponentFixture<ProficiencyDeletedListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProficiencyDeletedListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProficiencyDeletedListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
