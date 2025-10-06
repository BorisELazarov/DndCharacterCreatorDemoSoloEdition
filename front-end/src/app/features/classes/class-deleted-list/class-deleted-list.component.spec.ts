import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassDeletedListComponent } from './class-deleted-list.component';

describe('ClassDeletedListComponent', () => {
  let component: ClassDeletedListComponent;
  let fixture: ComponentFixture<ClassDeletedListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClassDeletedListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ClassDeletedListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
