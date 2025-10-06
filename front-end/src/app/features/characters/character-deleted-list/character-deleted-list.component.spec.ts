import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CharacterDeletedListComponent } from './character-deleted-list.component';

describe('CharacterDeletedListComponent', () => {
  let component: CharacterDeletedListComponent;
  let fixture: ComponentFixture<CharacterDeletedListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CharacterDeletedListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CharacterDeletedListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
