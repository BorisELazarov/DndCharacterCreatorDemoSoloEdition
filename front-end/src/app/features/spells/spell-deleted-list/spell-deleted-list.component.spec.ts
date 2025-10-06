import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpellDeletedListComponent } from './spell-deleted-list.component';

describe('SpellDeletedListComponent', () => {
  let component: SpellDeletedListComponent;
  let fixture: ComponentFixture<SpellDeletedListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpellDeletedListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SpellDeletedListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
